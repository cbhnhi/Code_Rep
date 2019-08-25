
#include <stdio.h>

//#include<cuda.h>
#include"cuda_runtime.h"
#include"device_launch_parameters.h"
//#include <opencv2/opencv.hpp>
//#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
//#include <opencv2/imgproc/imgproc.hpp>


__device__ int floorSqrt(int x);
 __global__ void CudaDoGradientWork(const unsigned char* d_inputArray, const unsigned char* d_outputArray, int numRows, int step, int numCols);
 void DoGradientWork(const unsigned char* inputArray, unsigned char* outputArray, int numRows, int step, int numCols);