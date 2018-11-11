
#include "PlayGround.cuh"

//#include"opencv2/opencv.hpp"
//#include"opencv2/gpu/gpu.hpp"

__global__ void DoGradientWork(const uchar* d_inputArray, uchar* d_outputArray,int numRows,int step,int numCols )
{
	int idX = threadIdx.x;
	int idY = threadIdx.y;
	int gdX = 0;
	int gdY = 0;
	//asuming 256 threads per block 
	for (int i = 0; i < numCols /256; i++)
	{
		if (blockIdx.x != 0)
		{
			if (idX != 0)
			{
				gdX += d_inputArray[(blockIdx.x - 1)*step + i * 256 - 1];
				gdY+= d_inputArray[(blockIdx.x - 1)*step + i * 256 - 1];
			}
			if (idX*i < numCols - 1)
			{
				gdX -= d_inputArray[(blockIdx.x - 1)*step + i * 256 + 1];
				gdY += d_inputArray[(blockIdx.x - 1)*step + i * 256 + 1];
			}
			gdY+= 2* d_inputArray[(blockIdx.x - 1)*step + i * 256 ];
		}
		if (blockIdx.x != numRows - 1)
		{
			if (idX != 0)
			{
				gdX += d_inputArray[(blockIdx.x + 1)*step + i * 256 - 1];
				gdY -= d_inputArray[(blockIdx.x + 1)*step + i * 256 - 1];
			}
			if (idX*i < numCols - 1)
			{
				gdX -= d_inputArray[(blockIdx.x + 1)*step + i * 256 + 1];
				gdY -= d_inputArray[(blockIdx.x + 1)*step + i * 256 + 1];
			}
			gdY -= 2 * d_inputArray[(blockIdx.x + 1)*step + i * 256];

		}
		if (idX != 0)
		{
			gdX += 2*d_inputArray[(blockIdx.x )*step + i * 256 - 1];
		}
		if (idX*i < numCols - 1)
		{
			gdX-=2* d_inputArray[(blockIdx.x )*step + i * 256 + 1];
		}
		
		d_outputArray[blockIdx.x*step + i * 256] = floorSqrt(gdX*gdX + gdY * gdY);
	}
}

__device__ int floorSqrt(int x)
{
	// Base cases 
	if (x == 0 || x == 1)
		return x;

	// Do Binary Search for floor(sqrt(x)) 
	int start = 1, end = x, ans;
	while (start <= end)
	{
		int mid = (start + end) / 2;

		// If x is a perfect square 
		if (mid*mid == x)
			return mid;

		// Since we need floor, we update answer when mid*mid is  
		// smaller than x, and move closer to sqrt(x) 
		if (mid*mid < x)
		{
			start = mid + 1;
			ans = mid;
		}
		else // If mid*mid is greater than x 
			end = mid - 1;
	}
	return ans;
}
