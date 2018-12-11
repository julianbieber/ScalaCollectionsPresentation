//
// Created by julian on 04/12/18.
//

#pragma once

#include <functional>
#include <chrono>
#include "BenchmarkResult.h"

template <typename A>
class Benchmark {
public:
    explicit Benchmark(size_t iterations) : iterations(iterations) {};

    BenchmarkResult run(std::string_view name, std::function<std::vector<A>(void)> create_vector, std::function<void(std::vector<A>&&)> f) {
        std::vector<long> runtimes;

        for(size_t i = 0; i < iterations; ++i) {
            auto vector = create_vector();

            auto start = std::chrono::system_clock::now();

            f(std::move(vector));

            auto end = std::chrono::system_clock::now();

            auto milliseconds = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
            runtimes.push_back(milliseconds.count());
        }

        return BenchmarkResult::from_runtimes(name, runtimes);

    };

private:
    const size_t iterations;
};