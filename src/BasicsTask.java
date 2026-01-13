import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicsTask {

  public static void main(String[] args) {

    final Employee mark = new Employee("Mark", 23, EnumSet.of(Department.JAVA));
    final Employee don = new Employee("Don", 33, EnumSet.of(Department.REACT, Department.ANGULAR));
    final Employee bill = new Employee("Bill", 55, EnumSet.allOf(Department.class));

    // Initialize list
    List<Employee> employees = null;

    // Group employee names by department
    Map<Department, List<String>> employeesInDepartments = null;
    System.out.println(employeesInDepartments);

    // Find any employee that belongs to the JAVA department and print his name
    String anyJavaDepartmentEmployeeNameOrEmpty = null;
    System.out.println(anyJavaDepartmentEmployeeNameOrEmpty);

    // Fix compilation error and apply to every employee in the employees
    EmployeeLogger employeeLogger = BasicsTask::log;
  }

  static void log(Employee employee) {
    System.out.println(employee);
  }
}

interface EmployeeLogger {

}

enum Department {
  JAVA, REACT, ANGULAR
}

class Employee {

  private final String name;
  private final int age;
  private final Set<Department> departments;

  public Employee(String name, int age, Set<Department> departments) {
    this.name = name;
    this.age = age;
    this.departments = departments;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Set<Department> getDepartments() {
    return departments;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", departments=" + departments +
        '}';
  }
}
