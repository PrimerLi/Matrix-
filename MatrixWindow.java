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
    private JTextArea showEigenvalues;
    private JScrollPane showEigenvaluesInScrollPane;
    private JTextField alert;
    private JButton changeDimension;
    private JButton restart;
    
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
	if (restart != null)
	{
	    panel.remove(this.restart);
	}
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
	okButton.addActionListener(new EnterMatrixListener());
	panel.setLayout(new GridLayout(3, 1));
	panel.add(okButton);
	panel.revalidate();
	panel.repaint();
    }

    private class EnterMatrixListener implements ActionListener
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
	    }
	    if (showEigenvaluesInScrollPane != null)
	    {
		panel.remove(showEigenvaluesInScrollPane);
	    }
	    if (inverseMatrixPanel != null)
	    {
		panel.remove(inverseMatrixPanel);
	    }
	    if (alert != null)
	    {
		panel.remove(alert);
	    }
	    if (changeDimension != null)
	    {
		panel.remove(changeDimension);
	    }
	    if (restart != null)
	    {
		panel.remove(restart);
	    }

	    panel.setLayout(new GridLayout(3, 2));

	    showDet = new JTextField("Det of your matrix = " + detOfMatrix(MatrixWindow.this.matrix));
	    showDet.setFont(new Font("cambria", Font.PLAIN, 24));
	    showDet.setColumns(30);
	    panel.add(showDet);

	    StringBuilder s = new StringBuilder();
	    Complex []eigenvector = eigenvalues(MatrixWindow.this.matrix);
	    s.append("Eigenvalues of your matrix are shown below: \n");
	    for (int i = 0; i < eigenvector.length; i++)
	    {
		s.append("# " + i + ": " + eigenvector[i].toString() + "\n");
	    }
	    showEigenvalues = new JTextArea(s.toString());
	    showEigenvalues.setFont(new Font("cambria", Font.PLAIN, 24));
	    showEigenvaluesInScrollPane = new JScrollPane(showEigenvalues);
	    panel.add(showEigenvaluesInScrollPane);

	    Matrix temp = new Matrix(MatrixWindow.this.matrix);
	    if (!temp.isSingular())
	    {
		inverseMatrixPanel = new MatrixPanel(temp.inverse());
		panel.add(inverseMatrixPanel);
	    }
	    else
	    {
		alert = new JTextField("This matrix is singular. ");
		alert.setFont(new Font("cambria", Font.PLAIN, 24));
		panel.add(alert);
	    }

	    changeDimension = new JButton("Change dimension");
	    changeDimension.setFont(new Font("cambria", Font.PLAIN, 24));
	    changeDimension.addActionListener(new ChangeDimensionListener());
	    panel.add(changeDimension);

	    panel.revalidate();
	    panel.repaint();
	}
    }

    private class ChangeDimensionListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    MatrixWindow.this.panel.removeAll();
	    MatrixWindow.this.restart = new JButton("Restart");
	    restart.setFont(new Font("cambria", Font.PLAIN, 24));
	    restart.addActionListener(MatrixWindow.this);
	    MatrixWindow.this.panel.setLayout(new GridLayout(2, 1));
	    MatrixWindow.this.panel.add(restart);
	    MatrixWindow.this.panel.revalidate();
	    MatrixWindow.this.panel.repaint();
	}
    }

    private static double detOfMatrix(double [][]matrix)
    {
	Matrix myMatrix = new Matrix(matrix);
	return myMatrix.detOfMatrix();
    }

    private static Complex [] eigenvalues(double [][]matrix)
    {
	Matrix myMatrix = new Matrix(matrix);
	return myMatrix.eigenvalues();
    }

    public static void main(String []args)
    {
	MatrixWindow window = new MatrixWindow();
	window.createButton("start", new Font("Serif", Font.PLAIN, 30));
	window.setVisible(true);
    }
}
