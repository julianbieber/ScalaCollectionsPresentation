#include <iostream>
#include <vector>
#include <hpx/include/parallel_transform.hpp>
#include "Benchmark.h"
#include "VectorView.h"
#include <hpx/hpx_main.hpp>

std::vector<long> generate_vector(size_t size) {
    std::vector<long> vector;
    vector.reserve(size);
    for (size_t i = 0; i < size; ++i) {
        vector.push_back(i);
    }
    return vector;
}

long plus_one(long i) {
    return i + 1;
}

long expensive(long nothing) {
    long fib = 1;
    long prevFib = 1;

    for(int i=2; i<1000; i++) {
        long temp = fib;
        fib+= prevFib;
        prevFib = temp;
    }
    return fib;
}

void foreach(const std::vector<long>& vector) {
    std::for_each(std::begin(vector), std::end(vector), [](volatile auto i) {

    });
}

void transform(const std::vector<long>& vector) {
    std::vector<long> destination;
    destination.reserve(vector.size());

    std::transform(std::begin(vector), std::end(vector), std::back_inserter(destination), plus_one);
}

void view_transform(std::vector<long>&& vector) {
    auto destination = VectorView<long, long>(std::move(vector), plus_one)
        .map<long>(plus_one)
        .map<long>(plus_one)
        .map<long>(plus_one)
        .map<long>(plus_one)
        .force();
}

void view_transform_one(std::vector<long>&& vector) {
    auto destination = VectorView<long, long>(std::move(vector), plus_one)
            .force();
}

void par_map(const std::vector<long>& vector) {
    std::vector<long> destination;
    destination.reserve(vector.size());

    hpx::parallel::transform(
            hpx::parallel::execution::parallel_policy(),
            std::begin(vector),
            std::end(vector),
            std::begin(destination),
            plus_one);
}

void print_result(const BenchmarkResult& result) {
    std::cout << result.name << " " << result.min << ", " << result.max << ", " << result.average << "\n";
}

int main() {
    Benchmark<long> benchmark(1000);

    print_result(benchmark.run("foreach", [](){ return generate_vector(100000000);}, foreach));
    print_result(benchmark.run("map", [](){ return generate_vector(100000000);}, transform));
    print_result(benchmark.run("view map", [](){ return generate_vector(100000000);}, view_transform));
    print_result(benchmark.run("view map one transformation", [](){ return generate_vector(100000000);}, view_transform_one));
    print_result(benchmark.run("par map", [](){ return generate_vector(100000000);}, par_map));
    return 0;
}