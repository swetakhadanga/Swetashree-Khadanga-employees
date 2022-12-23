package application;

import application.Repo.EmpRepo;
import application.Repo.EmpRepoImple;
import application.Services.EmpService;
import application.Services.EmpServiceImple;
import application.core.Engine;
import application.io.FileIO;
import application.io.FileIOImple;
import application.io.OutputWriterConsole;
import application.io.Writer;

public class EmployessWorkedTogether {
	public static void main(String[] args) {
		FileIO fileIO = new FileIOImple();
		Writer writer = new OutputWriterConsole();

		EmpRepo employeeRepository = new EmpRepoImple();
		EmpService emplService = new EmpServiceImple(employeeRepository);

		Engine engine = new Engine(fileIO, writer, emplService);
		engine.run();
	}
}
