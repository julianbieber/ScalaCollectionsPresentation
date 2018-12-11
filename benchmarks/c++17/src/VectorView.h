//
// Created by julian on 09/12/18.
//

#pragma once

#include <vector>
#include <functional>
#include <memory>
#include <hpx/include/parallel_transform.hpp>

template <typename A, typename B>
class VectorView {
public:
    VectorView(std::vector<A>&& vector, std::function<B(A)> transformation) :
        vector(std::move(vector)),
        transformation(std::move(transformation)) {
    };


    template <typename C> VectorView<A, C> map(std::function<C(B)> f) {
        return VectorView(std::move(vector), [&](const A& a){
           return f(transformation(a));
        });
    }

    std::vector<B> force() {
        std::vector<B> new_vector;
        new_vector.reserve(vector.size());

        std::transform(std::begin(vector), std::end(vector), std::back_inserter(new_vector), transformation);
        return new_vector;
    }

    std::vector<B> force_parallel() {
        std::vector<B> new_vector;
        new_vector.reserve(vector.size());

        hpx::parallel::transform(
                hpx::parallel::execution::parallel_policy(),
                std::begin(vector),
                std::end(vector),
                std::begin(new_vector),
                transformation);
        return new_vector;
    }

    std::vector<A> vector;
    const std::function<B(A)> transformation;
};