public class ComplexNumbers {
    double real;
    double imaginary;

    public ComplexNumbers(double real) {
        this.real = real;
        imaginary = 0;
    }

    public ComplexNumbers(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumbers sum(ComplexNumbers complexNumber) {
        return new ComplexNumbers(real + complexNumber.real,
                imaginary + complexNumber.imaginary);
    }

    public ComplexNumbers sub(ComplexNumbers complexNumber) {
        return new ComplexNumbers(real - complexNumber.real,
                imaginary - complexNumber.imaginary);
    }

    public ComplexNumbers mul(ComplexNumbers complexNumber) {
        double resultReal = real * complexNumber.real - imaginary * complexNumber.imaginary;
        double resultImaginary = real * complexNumber.imaginary + imaginary * complexNumber.real;
        return new ComplexNumbers(resultReal, resultImaginary);
    }

    public ComplexNumbers abs() {
        return new ComplexNumbers(Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2)));
    }

    @Override
    public String toString() {
        return (real == 0 ? "" : real) +
                (imaginary < 0 ? imaginary + "i" : (imaginary == 0 ? "" :
                        (real == 0 ? imaginary + "i" : "+" + imaginary + "i")));
    }
}