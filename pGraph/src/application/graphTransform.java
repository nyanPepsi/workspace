package application;

import java.io.FileInputStream;
import java.util.Scanner;

public class graphTransform {
	int k = 0;
	double adjMatrix[][];
	double trMatrix[][];
	double A[][];
	double eVec[][];
	double eVals[];
	double eps = 0.000001;
	double x[];
	double y[];
	double res[];
	
	public graphTransform() {
		// TODO Auto-generated constructor stub
	}
	
	public void read(String fileName) {
		try {
			FileInputStream in = new FileInputStream(fileName);
			Scanner sc = new Scanner (in);
			
			int nRows = sc.nextInt();
			adjMatrix = new double [nRows][nRows];
			
			for (int i = 0; i < nRows; ++i){			
				sc.hasNext();
				for (int j = 0; j < nRows; ++j) {
					double x = sc.nextDouble();
					adjMatrix[i][j] = x;
				}
			}
			sc.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void build_A(){
		A = new double[adjMatrix.length][adjMatrix.length];
		
		double deg[] = new double [A.length];
		for (int i = 0; i < deg.length; ++i) {
			deg[i] = 0;
		}

		for (int i = 0; i < A.length; ++i) {
			for (int j = i + 1; j < A.length; ++j) {
				if (adjMatrix[i][j] != 0) {
					A[i][j] = A[j][i] = -1;
					++deg[i];
					++deg[j];
				}
				else {
					A[i][j] = A[j][i] = 0;
				}
			}
		}

		for (int i = 0; i < A.length; ++i) {
			A[i][i] = deg[i];
		}
		
		trMatrix = new double [A.length][];
		for (int i = 0; i < A.length; ++i) {
			trMatrix[i] = A[i].clone();
		}
	}
	
	public void mult(double A[][], double B[][], double res[][]){
		for (int i = 0; i < A.length; ++i) {
			for (int j = 0; j < B[0].length; ++j) {
				for (int k = 0; k < B.length; ++k) {
					res[i][j] += A[i][k] * B[k][j];
				}
			}
		}
	}
	
	public void Jacobi () {
		final double pi = 3.1415926535897932384626433832795;
		
		eVec = new double [A.length][A.length];
		for (int i = 0; i < eVec.length; ++i) {
			for (int j = 0; j < eVec.length; ++j) {
				eVec[i][j] = 0;
			}
			eVec[i][i] = 1;
		}
		
		double t = 0;
		for (int i = 0; i < A.length; ++i) {
			for (int j = 0; j < A.length; ++j) {
				if (i != j) {
					t += A[i][j] * A[i][j];
				}
			}
		}
		
		while (t >= eps) {
			++k;
			
			//Поиск максимального элемента
			int max_i = 0;
			int max_j = 0;
			double maxv = 0;
			for (int i = 0; i < A.length; ++i) {
				for (int j = i + 1; j < A.length; ++j) {
					if (Math.abs(A[i][j]) > Math.abs(maxv)) {
						maxv = A[i][j];
						max_i = i;
						max_j = j;
					}
				}
			}

			//Вычисление Sin и Cos
			double phi = 0.5 * Math.atan((2 * A[max_i][max_j]) / (A[max_i][max_i] - A[max_j][max_j]));
			double c, s;
			if (Math.abs(A[max_i][max_i] - A[max_j][max_j]) <= eps) {
				c = Math.cos(pi / 4);
				s = Math.sin(pi / 4);
			}
			else {
				c = Math.cos(phi);
				s = Math.sin(phi);
			}

			
			//Преобразование матрицы A
			double[][] _A = new double[A.length][];
			double[][] _eVec = new double[eVec.length][];
			
			for (int i = 0; i < A.length; ++i) {
				_A[i] = A[i].clone();
				_eVec[i] = eVec[i].clone();
			}
			
			for (int l = 0; l < A.length; ++l) {
				_A[max_i][l] = _A[l][max_i] = A[max_i][l] * c + A[max_j][l] * s;
				_A[max_j][l] = _A[l][max_j] = -A[max_i][l] * s + A[max_j][l] * c;
				
				_eVec[l][max_i] = eVec[l][max_i] * c + eVec[l][max_j] * s;
				_eVec[l][max_j] = -eVec[l][max_i] * s + eVec[l][max_j] * c;
			}

			double Aii = A[max_i][max_i];
			double Aij = A[max_i][max_j];
			double Aji = A[max_j][max_i];
			double Ajj = A[max_j][max_j];

			_A[max_i][max_i] = (Aii * c + Aji * s) * c + (Aij * c + Ajj * s) * s;
			_A[max_i][max_j] = _A[max_j][max_i] = (-Aii * s + Aji * c) * c + (-Aij * s + Ajj * c) * s;
			_A[max_j][max_j] = -(-Aii * s + Aji * c) * s + (-Aij * s + Ajj * c) * c;
			
			
			t -= 2 * A[max_i][max_j] * A[max_i][max_j];
			
			for (int i = 0; i < A.length; ++i) {
				A[i] = _A[i].clone();
				eVec[i] = _eVec[i].clone();
			}
			
			

		}
		
		//заполнение вектора собственных значений
		eVals = new double[A.length];
		
		for (int i = 0; i < A.length; ++i) {
				eVals[i] = A[i][i];
		}		
	}//end of Jacobi
		
	
	public void transform() {
		build_A();
		Jacobi();
		
		int firstVec = 0;
		double minVal1 = 2147483647;
		for (int i = 0; i < eVals.length; ++i) {
			if (eVals[i] > eps && eVals[i] < minVal1) {
				minVal1 = eVals[i];
				firstVec = i;
			}
		}
		
		
		int secondVec = 0;
		double minVal2 = 2147483647;
		for (int i = 0; i < eVals.length; ++i) {
			if ((eVals[i] > minVal1) && (eVals[i] < minVal2) && (eVals[i] - minVal1 > eps)) {
				minVal2 = eVals[i];
				secondVec = i;
			}
		}
		x = new double[eVec.length];
		y = new double[eVec.length];
		
		
		
		for (int i = 0; i < eVals.length; ++i) {
			x[i] = eVec[i][firstVec];
			y[i] = eVec[i][secondVec];
		}
		
		res = new double[trMatrix.length];
		for (int i = 0; i < trMatrix.length; ++i) {
			res[i] = 0;
			for (int j = 0; j < trMatrix.length; ++j) {
				res[i] += trMatrix[i][j] * y[j];
				
			}
			res[i] -= eVals[secondVec] * y[i];
			
		}
		
	}
}
