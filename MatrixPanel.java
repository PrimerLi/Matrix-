import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class MatrixPanel extends JPanel
{
    private int matrixDimension;
    private JTextField [][]matrix;

    public MatrixPanel(int dimension)
    {
	matrixDimension = dimension;
	matrix = new JTextField[matrixDimension][matrixDimension];
	this.setLayout(new GridLayout(matrixDimension, matrixDimension));
	for (int i = 0; i < matrixDimension; i++)
	{
	    for (int j = 0; j < matrixDimension; j++)
	    {
		JTextField temp = new JTextField();
		temp.setColumns(10);
		matrix[i][j] = temp;
		this.add(temp);
	    }
	}
    }

    public JTextField [][] getMatrix()
    {
	return matrix;
    }
}
