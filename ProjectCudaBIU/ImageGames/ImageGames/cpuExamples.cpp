
#include "stdafx.h"
#include "cpuExamples.h"
//#include"cuda.h"
//#include"cuda_runtime.h"
//#include"device_launch_parameters.h"
#include "..\TestCuda\Playground.cuh"

//#pragma warning(disable:C4996)
using namespace std;
using namespace cv;
	cpuExamples::cpuExamples()
	{
		doWork();
	}


	cpuExamples::~cpuExamples()
	{
	}

	void cpuExamples::doWork()
	{
		string filename2 = "D:\\Benjamin\\Code_Rep\\ProjectCudaBIU\\1.jpg";
		string filename = "D:\\Benjamin\\Code_Rep\\ProjectCudaBIU\\2.jpg";

		//cudaError_t cudaStatus;

		// Choose which GPU to run on, change this on a multi-GPU system.
		//cudaStatus = cudaSetDevice(0);
		//if (cudaStatus != cudaSuccess) {
			//fprintf(stderr, "cudaSetDevice failed!  Do you have a CUDA-capable GPU installed?");
		//}

		//VideoCapture vc;
		//if (!vc.open(filename2))
		//{
		//	cout << "failed to open video for read/n";
		//};
		Mat c=imread(filename2, CV_LOAD_IMAGE_COLOR);
		// vc>>c;
		Mat a(1920,1080,CV_8UC3);
		Mat b = imread(filename, CV_LOAD_IMAGE_COLOR);
		//a = imread(filename);
		uchar* raw = b.data;
		Mat color[3];
		Mat color2[3];
		//Mat color3[3];
		split(b, color);
		split(b, color2);
		uint gX;
		uint gY;
		uint final;
		uchar raw3;

		
		DoGradientWork(color[1].data,&raw3 , color[1].rows, color[1].step, color[1].cols);
		uchar*raw2 = color[1].data;
		for (int k = 0; k < 3; k++)
		{
			for (int i = 0; i < b.rows; i++)
			{
				for (int j = 0; j < b.cols; j++)
				{
					if (i == 0 || i == b.rows - 1 || j == 0 || j == b.cols - 1)
					{
						gX = 0;
						gY = 0;
					}
					else
					{
						gX = color[k].data[(i - 1)*color[k].step + j - 1] + 2 * color[k].data[i*color[k].step + j - 1] + color[k].data[(i + 1)*color[k].step + j - 1] - color[k].data[(i - 1)*color[k].step + j + 1] - 2 * color[k].data[i*color[k].step + j + 1] - color[k].data[(i + 1)*color[k].step + j + 1];
						gY = color[k].data[(i - 1)*color[k].step + j - 1] + 2 * color[k].data[(i - 1)*color[k].step + j] + color[k].data[(i + 1)*color[k].step + j + 1] - color[k].data[(i + 1)*color[k].step + j - 1] - 2 * color[k].data[(i + 1)*color[k].step + j] - color[k].data[(i + 1)*color[k].step + j + 1];
						color2[k].data[i*color[k].step + j] = floorSqrt(gY*gY + gX * gX);
					}
				}
			}
		}
		//vector<cv::Mat> array_to_merge;

		//array_to_merge.push_back(color2[0]);
		//array_to_merge.push_back(color2[1]);
		//array_to_merge.push_back(color2[2]);

		//cv::Mat color;
		////merge()
		//merge(array_to_merge, color);

		//(1920, 1080, CV_8UC1);
		
		int k = 0;

	}

	

	int cpuExamples::floorSqrt(int x)
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