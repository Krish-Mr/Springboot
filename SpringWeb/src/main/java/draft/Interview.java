package draft;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Interview {

	public static void main(String[] args) {
		List<Employee> asList = Arrays.asList(
				new Interview.Employee("Gokul", 70_000, "Bengaluru"),
				new Interview.Employee("Divya", 90_000, "Bengaluru"),
				new Interview.Employee("Arun", 80_000, "Chennai"),
				new Interview.Employee("Naveen", 90_000, "Chennai"),
				new Interview.Employee("Karthik", 70_000, "Chennai")
				);
	}

	static class Employee{
		private String name;
		private double salary;
		private String city;

		public Employee(String name, double salary, String city){
			this.name = name;
			this.salary = salary;
			this.city = city;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getSalary() {
			return salary;
		}
		public void setSalary(double salary) {
			this.salary = salary;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		@Override
		public String toString() {
			return "Employee [name=" + name + ", salary=" + salary + ", city=" + city + "]";
		}
	}

}
