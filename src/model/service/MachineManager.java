package model.service;

import model.entity.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 11/25/16.
 */
@Repository
@Transactional
public class MachineManager{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    private static Logger logger = Logger.getLogger(MachineManager.class.getName());

    public boolean registerMachine(Machine machine) throws Exception {
        entityManager.persist(machine);

        return true;
    }

    public boolean useMachine(Section section, MachineGroup machineGroup, Machine machine) throws Exception {
        Machine machine1 = getMachine(section, machineGroup, machine);
        int temp = machine.getMachineTimes().size();
        for (MachineTime machineTime : machine1.getMachineTimes()) {
            for (MachineTime time : machine.getMachineTimes()) {
                if(machineTime.getDate().equals(time.getDate())){
                    int tempShift = time.getMachineShifts().size();
                    for (MachineShift machineShift : machineTime.getMachineShifts()) {
                        for (MachineShift shift : time.getMachineShifts()) {
                            if (machineShift.getShift() == shift.getShift()){
                                if (machineShift.getTotalDate() >= shift.getTotalDate()){
                                    machineShift.setTotalDate(machineShift.getTotalDate() - shift.getTotalDate());
                                    tempShift--;
                                }else {
                                    return false;
                                }
                            }
                        }
                    }
                    if (tempShift != 0){
                        return false;
                    }
                    temp--;
                }
            }
        }
        if (temp != 0){
            return false;
        }
        entityManager.persist(machine1);

        return true;
    }

    public boolean useRemain(Section section, MachineGroup machineGroup, Machine machine) throws Exception {
        Machine machine1 = getMachine(section, machineGroup, machine);
        for (MachineTime machineTime : machine1.getMachineTimes()) {
            for (MachineTime time : machine.getMachineTimes()) {
                if(machineTime.getDate().equals(time.getDate())){
                    for (MachineShift machineShift : machineTime.getMachineShifts()) {
                        for (MachineShift shift : time.getMachineShifts()) {
                            if (machineShift.getShift() == shift.getShift()){
                                if (machineShift.getRemain() >= shift.getRemain()){
                                    machineShift.setRemain(machineShift.getRemain() - shift.getRemain());
                                }else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        entityManager.persist(machine1);

        return true;
    }

    public boolean enterMachine(Section section, MachineGroup machineGroup, Machine machine) throws Exception {
        try{
            Machine machine1 = getMachine(section, machineGroup, machine);
            int temp = machine.getMachineTimes().size();
            for (MachineTime machineTime : machine1.getMachineTimes()) {
                for (MachineTime time : machine.getMachineTimes()) {
                    if (machineTime.getDate().equals(time.getDate())){
                        int tempShift = time.getMachineShifts().size();
                        for (MachineShift machineShift : machineTime.getMachineShifts()) {
                            for (MachineShift shift : time.getMachineShifts()) {
                                if (machineShift.getShift() == shift.getShift()){
                                    if (shift.getCapacity() != 0){
                                        machineShift.setCapacity(shift.getCapacity());
                                    }
                                    if (shift.getUph() != 0){
                                        machineShift.setUph(shift.getUph());
                                    }
                                    machineShift.setTotalDate(machineShift.getTotalDate() + shift.getTotalDate());
                                    shift.setShift(0);
                                    tempShift--;
                                }
                            }
                        }
                        if (tempShift != 0){
                            for (MachineShift machineShift : time.getMachineShifts()) {
                                if (machineShift.getShift() != 0){
                                    if (machineShift.getCapacity() == 0){
                                        machineShift.setCapacity(machine1.getCapacity());
                                    }
                                    if (machineShift.getUph() == 0){
                                        machineShift.setUph(machine1.getUph());
                                    }
                                    machineTime.getMachineShifts().add(machineShift);
                                }
                            }
                        }
                        time.setDate(new Date(0));
                        temp--;
                    }
                }
            }
            if(temp != 0){
                for (MachineTime machineTime : machine.getMachineTimes()) {
                    if (machineTime.getDate() != new Date(0)){
                        for (MachineShift machineShift : machineTime.getMachineShifts()) {
                            if (machineShift.getCapacity() == 0){
                                machineShift.setCapacity(machine1.getCapacity());
                            }
                            if (machineShift.getUph() == 0){
                                machineShift.setUph(machine1.getUph());
                            }
                        }
                        machine1.getMachineTimes().add(machineTime);
                    }
                }
            }
            entityManager.persist(machine1);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean setRemain(Section section, MachineGroup machineGroup, Machine machine) throws Exception {
        Machine machine1 = getMachine(section, machineGroup, machine);
        for (MachineTime machineTime : machine1.getMachineTimes()) {
            for (MachineTime time : machine.getMachineTimes()) {
                if (machineTime.getDate().equals(time.getDate())){
                    for (MachineShift machineShift : machineTime.getMachineShifts()) {
                        for (MachineShift shift : time.getMachineShifts()) {
                            if (machineShift.getShift() == shift.getShift()){
                                machineShift.setRemain(machineShift.getRemain() + shift.getRemain());
                            }
                        }
                    }
                }
            }
        }
        entityManager.persist(machine1);

        return true;
    }

    public boolean deleteMachine(Section section, MachineGroup machineGroup, Machine machine) throws Exception {
        machine = getMachine(section, machineGroup, machine);
        if (machine != null){
            entityManager.remove(machine);

            return true;
        }
        return false;
    }

    public boolean delelteTime(Machine machine, MachineTime machineTime) throws Exception {
        machineTime = getTime(machine, machineTime);
        if (machineTime != null){
            entityManager.remove(machineTime);

            return true;
        }
        return false;
    }

    public boolean edit(Machine machine) throws Exception {
        Machine machine1 = (Machine) entityManager.createQuery("select p from Machine p where p.id=:x")
                .setParameter("x", machine.getId())
                .getSingleResult();

        machine1.setCode(machine.getCode());
        machine1.setName(machine.getName());
        machine1.setCapacity(machine.getCapacity());
        machine1.setUph(machine.getUph());
        machine1.setBirthday(machine.getBirthday());
        machine1.setCountry(machine.getCountry());

        entityManager.persist(machine1);

        return true;
    }

    public Machine getMachine(Section section, MachineGroup machineGroup, Machine machine){
        try {
            String query = "select w from Section p join p.machineGroups t join t.machines w where ";

            if (section.getCode() != null){
                query += "p.code=:x and ";
            }else {
                query += "p.name=:x and ";
            }
            if (machineGroup.getCode() != null){
                query += "t.code=:y and ";
            }else {
                query += "t.name=:y and ";
            }
            if (machine.getCode() != null){
                query += "w.code=:z ";
            }else {
                query += "w.name=:z ";
            }
            Query query1 = entityManager.createQuery(query);

            if (section.getCode() != null){
                query1.setParameter("x", section.getCode());
            }else {
                query1.setParameter("x", section.getName());
            }
            if (machineGroup.getCode() != null){
                query1.setParameter("y", machineGroup.getCode());
            }else {
                query1.setParameter("y", machineGroup.getName());
            }
            if (machine.getCode() != null){
                query1.setParameter("z", machine.getCode());
            }else {
                query1.setParameter("z", machine.getName());
            }

            return (Machine) query1.getSingleResult();
        }catch (Exception e){
            logger.error(e);
            return null;
        }
    }

    public MachineTime getTime(Machine machine, MachineTime machineTime) throws Exception {
        MachineTime machineTime1 = (MachineTime) entityManager.createQuery("select t from Machine p join p.machineTimes t where p.code=:x and p.machineTimes.date=:y")
                .setParameter("x", machine.getName()).setParameter("y", machineTime.getDate()).getSingleResult();
        return machineTime1;
    }

    public List<Machine> getMachines() throws Exception {
        List<Machine> machines = entityManager.createQuery("select p from Machine p").getResultList();
        return machines;
    }

    public List<MachineTime> getTimes(Machine machine) throws Exception{
        return entityManager.createQuery("select t from Machine p join p.machineTimes t where p.code=:x")
                .setParameter("x", machine.getCode()).getResultList();
    }

    public List<MachineTime> getTimesBetween(Machine machine, Date start, Date end) throws Exception {
        return entityManager.createQuery("select t from Machine p join p.machineTimes t where p.code=:x and p.machineTimes.date between :s1 and :s2")
                .setParameter("x", machine.getCode()).setParameter("s1", start, TemporalType.DATE).setParameter("s2", end, TemporalType.DATE).getResultList();
    }

    public List<Machine> getAll(){
        try {
            return entityManager.createQuery("select p from Machine p").getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
