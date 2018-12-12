//
// https://www.johndcook.com/blog/standard_deviation/
#pragma once

#include <math.h>
#include <optional>


class RunningStat
{
public:
    RunningStat() : m_n(0) {}

    void clear()
    {
        m_n = 0;
    }

    void push(double x)
    {
        m_n++;

        // See Knuth TAOCP vol 2, 3rd edition, page 232
        if (m_n == 1)
        {
            m_oldM = m_newM = x;
            m_oldS = 0.0;
            min = x;
            max = x;
        }
        else
        {
            m_newM = m_oldM + (x - m_oldM)/m_n;
            m_newS = m_oldS + (x - m_oldM)*(x - m_newM);

            // set up for next iteration
            m_oldM = m_newM;
            m_oldS = m_newS;

            if (x < *min)
                min = x;
            if (x > * max)
                max = x;
        }
    }

    int numDataValues() const
    {
        return m_n;
    }

    double mean() const
    {
        return (m_n > 0) ? m_newM : 0.0;
    }

    double variance() const
    {
        return ( (m_n > 1) ? m_newS/(m_n - 1) : 0.0 );
    }

    double standardDeviation() const
    {
        return std::sqrt( variance() );
    }

    std::optional<double> min = {};
    std::optional<double> max = {};

private:
    int m_n;
    double m_oldM, m_newM, m_oldS, m_newS;
};