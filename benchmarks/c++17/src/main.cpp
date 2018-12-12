#include <iostream>
#include <vector>
#include <hpx/include/parallel_transform.hpp>
#include <hpx/hpx_init.hpp>
#include "Benchmark.h"
#include "VectorView.h"
//#include <hpx/hpx_main.hpp>

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
    std::cout << "| " << result.name << " | " << result.min << " | " << result.max << " | " << result.mean << " | " << result.variance << " | " << result.standardDeviation << " | \n";
}

int hpx_main(int argc, char ** argv) {
    Benchmark<long> benchmark(100);

    /*print_result(benchmark.run("foreach 1000000", [](){ return generate_vector(1000000);}, foreach));
    print_result(benchmark.run("foreach 10000000", [](){ return generate_vector(10000000);}, foreach));
    print_result(benchmark.run("foreach 100000000", [](){ return generate_vector(100000000);}, foreach));
    print_result(benchmark.run("map 1000000", [](){ return generate_vector(1000000);}, transform));
    print_result(benchmark.run("map 10000000", [](){ return generate_vector(10000000);}, transform));
    print_result(benchmark.run("map 100000000", [](){ return generate_vector(100000000);}, transform));
    print_result(benchmark.run("view map 1000000", [](){ return generate_vector(1000000);}, view_transform));
    print_result(benchmark.run("view map 10000000", [](){ return generate_vector(10000000);}, view_transform));
    print_result(benchmark.run("view map 100000000", [](){ return generate_vector(100000000);}, view_transform));
    print_result(benchmark.run("view map one transformation 1000000", [](){ return generate_vector(1000000);}, view_transform_one));
    print_result(benchmark.run("view map one transformation 10000000", [](){ return generate_vector(10000000);}, view_transform_one));
    print_result(benchmark.run("view map one transformation 100000000", [](){ return generate_vector(100000000);}, view_transform_one));
    */print_result(benchmark.run("par map 1000000", [](){ return generate_vector(1000000);}, par_map));
    print_result(benchmark.run("par map 10000000", [](){ return generate_vector(10000000);}, par_map));
    print_result(benchmark.run("par map 100000000", [](){ return generate_vector(100000000);}, par_map));
    return 0;
}

int main(int argc, char ** argv) {
    std::vector<std::string> cfg;
    cfg.push_back("hpx.os_threads=all");
    hpx::init(argc, argv, cfg);
    return 0;
}