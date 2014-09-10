public class Vector
{
    private int length;
    private double []array;

    public int getLength()
    {
	return this.length;
    }

    public Vector(double []argument)
    {
	length = argument.length;
	array = new double [length];
	for (int i = 0; i < length; i++)
	{
	    array[i] = argument[i];
	}
    }

    public Vector(Vector argument)
    {
	length = argument.length;
	array = new double [length];
	for (int i = 0; i < length; i++)
	{
	    array[i] = argument.array[i];
	}
    }

    public Vector(int length)
    {
	this.length = length;
	array = new double [length];
	for (int i = 0; i < length; i++)
	{
	    array[i] = 0;
	}
    }

    public Vector add(Vector argument)
    {
	if (this.length != argument.length)
	{
	    throw new IllegalArgumentException("Array length does not match.");
	}
	Vector temp = new Vector (argument.length);
	for (int i = 0; i < length; i++)
	{
	    temp.array[i] = this.array[i] + argument.array[i];
	}
	return temp;
    }

    public Vector subtract(Vector argument)
    {
	if (this.length != argument.length)
	{
	    throw new IllegalArgumentException("Array length does not match. ");
	}

	Vector temp = new Vector(length);
	for (int i = 0; i < length; i++)
	{
	    temp.array[i] = this.array[i] - argument.array[i];
	}
	return temp;
    }

    public double innerProductWith(Vector argument)
    {
	if (this.length != argument.length)
	{
	    throw new IllegalArgumentException("Array length does not match. ");
	}

	double sum = 0;
	for (int i = 0; i < length; i++)
	{
	    sum = sum + this.array[i]*argument.array[i];
	}
	return sum;
    }

    public void printVector()
    {
	for (int i = 0; i < length; i++)
	{
	    System.out.println(this.array[i]);
	}
    }

    public double norm()
    {
	double sum = 0;
	return Math.sqrt(this.innerProductWith(this));
    }

    public Vector scale(double factor)
    {
	Vector vector = new Vector(length);
	for (int i = 0; i < length; i++)
	{
	    vector.array[i] = array[i]*factor;
	}
	return vector;
    }

    public Vector normalize()
    {
	Vector vector = new Vector(length);
	return this.scale(1.0/this.norm());
    }

    public double element(int i)
    {
	if (i >= length)
	{
	    throw new IllegalArgumentException("Vector index out of bounds. ");
	}
	return this.array[i];
    }

    public static void main(String []args)
    {
	int length = 5;
	double []array = new double [length];
	for (int i = 0; i < length; i++)
	{
	    array[i] = i;
	}
	Vector a = new Vector(array);
	for (int i = 0; i < length; i++)
	{
	    array[i] = 2*i;
	}
	Vector b = new Vector(array);

	System.out.println("A vector : ");
	a.printVector();
	System.out.println("B vector : ");
	b.printVector();

	System.out.println("A + B");
	a.add(b).printVector();
	System.out.println("A - B");
	a.subtract(b).printVector();
	System.out.println("Innder product of A and B : " + a.innerProductWith(b));

	System.out.println("Normalized A is ");
	a.normalize().printVector();
	System.out.println("Normalized B is ");
	b.normalize().printVector();

	System.out.println("Norm of A is " + a.norm());
	System.out.println("Norm of B is " + b.norm());
    }
}
