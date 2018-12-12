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
    BenchmarkResult(std::string_view name, double min, double max, double mean, double variance, double standardDeviation) :
        name(name),
        min(min),
        max(max),
        mean(mean),
        variance(variance),
        standardDeviation(standardDeviation) {};

    const std::string name;
    const double min;
    const double max;
    const double mean;
    const double variance;
    const double standardDeviation;
};