public class Complex
{
    private double real;
    private double imag;

    public Complex(double x, double y)
    {
	real = x;
	imag = y;
    }

    public Complex(double x)
    {
	real = x;
	imag = 0;
    }

    public Complex(Complex argument)
    {
	real = argument.real;
	imag = argument.imag;
    }

    public static Complex toComplex(double x, double y)
    {
	return new Complex(x, y);
    }

    public static Complex toComplex(double x)
    {
	return new Complex(x, 0);
    }

    public Complex add(Complex argument)
    {
	return new Complex(this.real + argument.real, this.imag + argument.imag);
    }

    public Complex subtract(Complex argument)
    {
	return new Complex(this.real - argument.real, this.imag - argument.imag);
    }

    public Complex multiply(Complex argument)
    {
	double x = real*argument.real - imag*argument.imag;
	double y = real*argument.imag + imag*argument.real;
	return new Complex(x, y);
    }

    public Complex conjugate()
    {
	return new Complex(this.real, -this.imag);
    }

    public double norm()
    {
	return Math.sqrt(real*real + imag*imag);
    }

    public double arg()
    {
	return Math.atan2(this.imag, this.real);
    }

    public Complex divide(Complex argument)
    {
	return (this.multiply(argument.conjugate())).scale(1.0/(argument.norm()*argument.norm()));
    }

    public Complex scale(double factor)
    {
	return new Complex(factor*this.real, factor*this.imag);
    }

    public double getReal()
    {
	return this.real;
    }

    public double getImag()
    {
	return this.imag;
    }

    public String toString()
    {
	if (this.real == 0)
	{
	    if (this.imag == 1) 
	    {
		StringBuilder s = new StringBuilder("i");
		return s.toString();
	    }
	    else if (this.imag == -1)
	    {
		StringBuilder s = new StringBuilder("-i");
		return s.toString();
	    }
	    else if (this.imag == 0)
	    {
		return String.valueOf(0);
	    }
	    else 
	    {
		StringBuilder s = new StringBuilder(this.imag + " i");
		return s.toString();
	    }
	}
	if (this.imag > 0)
	{
	    StringBuilder s = new StringBuilder(this.real + " + " + this.imag + " i");
	    return s.toString();
	}
	else if (this.imag == 0)
	{
	    return String.valueOf(this.real);
	}
	else
	{
	    StringBuilder s = new StringBuilder(this.real + " - " + (-this.imag) + " i");
	    return s.toString();
	}
    }

    public static void main(String []args)
    {
	Complex z = new Complex(1, 1.2);
	System.out.println("This is your complex number : " + z);
	System.out.println("Complex conjugate of " + z + " is " + z.conjugate());
	System.out.println("Norm = " + z.norm() + ", arg = " + z.arg());
	System.out.println("Real = " + z.getReal() + ", imag = " + z.getImag());

	Complex zeta = new Complex(-1, 0.5);
	System.out.println("Product of " + z + " and " + zeta + " is " + z.multiply(zeta));
	System.out.println("Quotient is " + z.divide(zeta));
	System.out.println("Sum is " + z.add(zeta));
	System.out.println("Difference is " + z.subtract(zeta));
    }
}
