package application.Repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import application.model.EmpRecord;
import application.Repo.EmpRepo;

public class EmpRepoImple implements EmpRepo {

    private List<EmpRecord> db;
    
    public EmpRepoImple() {
        this.db = new ArrayList<>();
    }

    @Override
    public void save(EmpRecord record) {
        this.db.add(record);
    }

    @Override
    public void saveAll(Collection<EmpRecord> records) {
        this.db.addAll(records);
    }

    @Override
    public List<EmpRecord> getAllRecords() {
        return Collections.unmodifiableList(this.db);
    }


}

