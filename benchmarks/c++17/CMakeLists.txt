cmake_minimum_required(VERSION 3.12)
project(c__CollectionsBenchmark)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "-Wall -Wextra -O3")

find_package(HPX REQUIRED)

add_hpx_executable(benchmark
        ESSENTIAL
        SOURCES src/main.cpp src/Benchmark.h src/BenchmarkResult.h src/VectorView.h
        COMPONENT_DEPENDENCIES)
