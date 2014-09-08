import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MatrixWindow implements ActionListener
{
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private MatrixPanel matrixPanel;
    private MatrixPanel inverseMatrixPanel;
    private int matrixDimension;
    private double [][]matrix;
    private JTextField showDet;
    private JTextField alert;
    
    public MatrixWindow()
    {
	matrixDimension = 0;
	panel = new JPanel();
	panel.setLayout(new GridLayout(2, 1));
	frame = new JFrame("Matrix calculator");
	frame.setSize(new Dimension(800, 600));
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	frame.setLocationRelativeTo(null);
	frame.setContentPane(panel);
    }

    public void setSize(Dimension dimension)
    {
	frame.setSize(dimension);
    }

    private void setVisible(boolean isVisible)
    {
	frame.setVisible(isVisible);
    }

    private void createButton(String text, Font font)
    {
	this.button = new JButton(text);
	this.button.setFont(font);
	button.addActionListener(this);
	this.panel.add(this.button);
    }

    public void actionPerformed(ActionEvent e)
    {
	matrixDimension = Integer.parseInt(JOptionPane.showInputDialog("Please enter your matrix dimension"));
	while(matrixDimension < 1 || matrixDimension > 15)
	{
	    matrixDimension = Integer.parseInt(JOptionPane.showInputDialog("Matrix dimension should lie within [1, 15]"));
	}
	JOptionPane.showMessageDialog(this.frame, "Please enter your matrix elements");
	panel.remove(this.button);
	matrixPanel = new MatrixPanel(matrixDimension);
	panel.add(matrixPanel);
	panel.revalidate();
	panel.repaint();
	for (int i = 0; i < matrixDimension; i++)
	{
	    for (int j = 0; j < matrixDimension; j++)
	    {
		matrixPanel.getMatrix()[i][j].setText(String.valueOf(0));
	    }
	}

	JButton okButton = new JButton("OK");
	okButton.setFont(new Font("serif", Font.PLAIN, 24));
	okButton.addActionListener(new enterMatrixListener());
	panel.setLayout(new GridLayout(3, 1));
	panel.add(okButton);
	panel.revalidate();
	panel.repaint();
    }

    private class enterMatrixListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    matrix = new double [matrixDimension][matrixDimension];
	    for (int i = 0; i < matrixDimension; i++)
	    {
		for (int j = 0; j < matrixDimension; j++)
		{
		    matrix[i][j] = Double.parseDouble(matrixPanel.getMatrix()[i][j].getText());
		}
	    }
	    if (showDet != null)
	    {
		panel.remove(showDet);
		panel.revalidate();
		panel.repaint();
	    }
	    if (inverseMatrixPanel != null)
	    {
		panel.remove(inverseMatrixPanel);
		panel.revalidate();
		panel.repaint();
	    }
	    if (alert != null)
	    {
		panel.remove(alert);
		panel.revalidate();
		panel.repaint();
	    }

	    panel.setLayout(new GridLayout(4, 1));
	    showDet = new JTextField("Det of your matrix = " + detOfMatrix(MatrixWindow.this.matrix));
	    showDet.setFont(new Font("serif", Font.PLAIN, 24));
	    showDet.setColumns(30);
	    panel.add(showDet);

	    Matrix temp = new Matrix(MatrixWindow.this.matrix);
	    if (!temp.isSingular())
	    {
		inverseMatrixPanel = new MatrixPanel(temp.inverse());
		panel.add(inverseMatrixPanel);
	    }
	    else
	    {
		alert = new JTextField("This matrix is singular. ");
		alert.setFont(new Font("serif", Font.PLAIN, 24));
		panel.add(alert);
	    }

	    panel.revalidate();
	    panel.repaint();
	}
    }

    private static double detOfMatrix(double [][]matrix)
    {
	Matrix myMatrix = new Matrix(matrix);
	return myMatrix.detOfMatrix();
    }

    public static void main(String []args)
    {
	MatrixWindow window = new MatrixWindow();
	window.createButton("start", new Font("Serif", Font.PLAIN, 24));
	window.setVisible(true);
    }
}
