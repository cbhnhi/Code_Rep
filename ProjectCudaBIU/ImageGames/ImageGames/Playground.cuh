#include <iostream>
#include<cuda.h>
#include<cuda_runtime.h>
#include"device_launch_parameters.h"
#include"cpuExamples.h"

int floorSqrt(int x);
 void DoGradientWork(const uchar* d_inputArray, const uchar* d_outputArray, int numRows, int step, int numCols);