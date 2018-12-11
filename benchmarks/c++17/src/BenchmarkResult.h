//
// Created by julian on 04/12/18.
//

#pragma once

#include <vector>
#include <numeric>
#include <iostream>
#include <algorithm>

class BenchmarkResult {
public:
    static BenchmarkResult from_runtimes(std::string_view name, const std::vector<long>& runtimes) {
        auto min = *std::min_element(std::begin(runtimes), std::end(runtimes));
        auto max = *std::max_element(std::begin(runtimes), std::end(runtimes));
        auto avg = calculate_average(runtimes);
        return BenchmarkResult(name, min, max, avg, 0.0);

    }

    static double calculate_average(const std::vector<long>& runtimes) {
        return std::accumulate(std::begin(runtimes), std::end(runtimes), std::pair<double, double>(0.0, 1.0), [](const std::pair<double, double>& acc_counter, long i) {
            auto& [acc, counter] = acc_counter;
            return std::make_pair(acc + (i - acc) / counter, counter + 1);
        }).first;
    }

    BenchmarkResult(std::string_view name, long min, long max, double average, double standardDeviation) :
        name(name),
        min(min),
        max(max),
        average(average),
        standardDeviation(standardDeviation) {};

    const std::string name;
    const long min;
    const long max;
    const double average;
    const double standardDeviation;
};