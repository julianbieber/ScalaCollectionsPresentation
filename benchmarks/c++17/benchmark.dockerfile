FROM voidlinux/voidlinux

RUN xbps-install -Suvy && xbps-install -Suvy && xbps-install -Suvy cmake gcc libatomic-devel libhwloc-devel make openmpi-devel libunwind-devel gperftools-devel boost-devel git
RUN git clone https://github.com/STEllAR-GROUP/hpx.git /opt/hpx
RUN g++ --version
RUN mkdir /opt/hpx_build && cd /opt/hpx_build && cmake /opt/hpx -DHPX_WITH_CXX17=true && make && make install

ADD src /opt/src
ADD CMakeLists.txt /opt

WORKDIR /opt

RUN cmake .
RUN make

ENTRYPOINT /opt/benchmark
# && gprof /opt/benchmark gmon.out > analysis.txt && cat analysis.txt