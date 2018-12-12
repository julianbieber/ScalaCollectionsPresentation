//
// Created by julian on 04/12/18.
//

#pragma once

#include <functional>
#include <chrono>
#include "BenchmarkResult.h"
#include "RunningStat.h"

template <typename A>
class Benchmark {
public:
    explicit Benchmark(size_t iterations) : iterations(iterations) {};

    BenchmarkResult run(std::string_view name, std::function<std::vector<A>(void)> create_vector, std::function<void(std::vector<A>&&)> f) {
        RunningStat stat;
        for(size_t i = 0; i < iterations; ++i) {
            auto vector = create_vector();

            auto start = std::chrono::system_clock::now();

            f(std::move(vector));

            auto end = std::chrono::system_clock::now();

            auto microseconds = std::chrono::duration_cast<std::chrono::microseconds>(end - start);
            stat.push((double) microseconds.count() / 1000.0);
        }

        return BenchmarkResult(name, *stat.min, *stat.max, stat.mean(), stat.variance(), stat.standardDeviation());
    };

private:
    const size_t iterations;
};

