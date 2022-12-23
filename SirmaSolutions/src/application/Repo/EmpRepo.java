package application.Repo;

import java.util.Collection;
import java.util.List;

import application.model.EmpRecord;

public interface EmpRepo {

    void save(EmpRecord record);

    List<EmpRecord> getAllRecords();

	void saveAll(Collection<EmpRecord> records);

}