//Aidan Johnson 1890052

public class ConsultantEmployee extends Employee {
    private double consultingFee; // Consulting Fee
    private double wage; // wage per hour
    private double hours; // hours worked for week

    public ConsultantEmployee(String firstName, String lastName, String socialSecurityNumber, double wage, double hours, double consultingFee) {
        super(firstName, lastName, socialSecurityNumber);

        if (wage < 0.0) { // validate wage
            throw new IllegalArgumentException("Hourly wage must be >= 0.0");
        }

        if ((hours < 0.0) || (hours > 168.0)) { // validate hours
            throw new IllegalArgumentException(
                    "Hours worked must be >= 0.0 and <= 168.0");
        }

        if (consultingFee < 0.0) { // validate consulting fee
            throw new IllegalArgumentException("Consulting Fee must be >= 0.0");
        }
        this.wage = wage;
        this.hours = hours;
        this.consultingFee = consultingFee;
    }

    // set consulting fee
    public void setConsultingFee(double conFee) {
        if (hours > 20) { // Consulting Fee / Hour maxes out at over 20 hours.
            conFee = conFee * 20;

        }
        this.consultingFee = conFee;
    }

    // return consulting fee
    public double getConsultingFee() {
        return this.consultingFee;
    }


    // set wage
    public void setWage(double wage) {
        if (wage < 0.0) { // validate wage
            throw new IllegalArgumentException("Hourly wage must be >= 0.0");
        }

        this.wage = wage;
    }

    // return wage
    public double getWage() {
        return wage;
    }

    // set hours worked
    public void setHours(double hours) {
        if ((hours < 0.0) || (hours > 168.0)) { // validate hours
            throw new IllegalArgumentException(
                    "Hours worked must be >= 0.0 and <= 168.0");
        }

        this.hours = hours;
    }

    // return hours worked
    public double getHours() {
        return hours;
    }

    // calculate earnings; override abstract method earnings in Employee
    @Override
    public double earnings() {
        return (consultingFee + (hours * wage));
    }

    @Override
    public double corpFundContribution() {
        return (consultingFee * 0.05);
    }

    // return String representation of ConsultantEmployee object
    @Override
    public String toString() {
        return String.format("Consultant Employee: %s%n%s: $%,.2f; %s: %,.2f %s: $%,.2f",
                super.toString(), "hourly wage", getWage(),
                "hours worked", getHours(),
                "consultant fee", getConsultingFee());
    }
}
