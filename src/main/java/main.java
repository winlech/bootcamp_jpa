import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class main {

    public static void main(String[] args) {
        Dentist dentista1 = new Dentist("Destista1", "Um", "endereco1", "1234", LocalDate.of(1995, Month.AUGUST, 12), "78728718732", "email1@algo.com", "seilaoqeisso");
        Dentist dentista2 = new Dentist("Destista2", "Dois", "endereco2", "453453", LocalDate.of(1994, Month.JANUARY, 5), "34234", "email2@algo.com", "seilaoqeisso");
        Dentist dentista3 = new Dentist("Destista3", "Tres", "endereco3", "124534534", LocalDate.of(1993, Month.JULY, 10), "4234234", "email3@algo.com", "seilaoqeisso");
        Dentist dentista4 = new Dentist("Destista4", "Quatro", "endereco4", "153453234", LocalDate.of(1992, Month.NOVEMBER, 16), "78728742342318732", "email4@algo.com", "seilaoqeisso");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("aprendendo_jpa");
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(dentista1);
        em.persist(dentista2);
        em.persist(dentista3);
        em.persist(dentista4);
        em.getTransaction().commit();

        Diary diary1 = new Diary("08:30:00", "09:00:00", dentista1);
        Diary diary2 = new Diary("09:30:00", "09:45:00", dentista1);
        Diary diary3 = new Diary("08:30:00", "09:15:00", dentista3);
        Diary diary4 = new Diary("09:30:00", "10:45:00", dentista2);
        Diary diary5 = new Diary("08:00:00", "09:00:00", dentista3);
        Diary diary6 = new Diary("10:30:00", "11:45:00", dentista3);
        Diary diary7 = new Diary("08:00:00", "09:00:00", dentista4);
        Diary diary8 = new Diary("09:10:00", "10:20:00", dentista4);

        em.getTransaction().begin();
        em.persist(diary1);
        em.persist(diary2);
        em.persist(diary3);
        em.persist(diary4);
        em.persist(diary5);
        em.persist(diary6);
        em.persist(diary7);
        em.persist(diary8);
        em.getTransaction().commit();

        Patient patient2 = new Patient("paciente3", "Sobrenome3", "endereco1", "321", LocalDate.of(2020, Month.JULY, 23),  "99424231", "email@email.com");
        Patient patient3 = new Patient("paciente3", "Sobrenome4", "endereco1", "321", LocalDate.of(2020, Month.JULY, 24),  "99424231", "email@email.com");
        Patient patient1 = new Patient("paciente1", "Sobrenome1", "endereco1", "321", LocalDate.of(2020, Month.JULY, 21), "99424231", "email@email.com");
        Patient patient4 = new Patient("paciente2", "Sobrenome2", "endereco1", "321", LocalDate.of(2020, Month.JULY, 22), "99424231", "email@email.com");

        em.getTransaction().begin();
        em.persist(patient1);
        em.persist(patient2);
        em.persist(patient3);
        em.persist(patient4);
        em.getTransaction().commit();
        
        TurnStatus turnStatus1 = new TurnStatus("turno 1", "finalizado");
        TurnStatus turnStatus2 = new TurnStatus("turno 2", "pendente");
        TurnStatus turnStatus3 = new TurnStatus("turno 3", "remarcado");

        em.getTransaction().begin();
        em.persist(turnStatus1);
        em.persist(turnStatus2);
        em.persist(turnStatus3);
        em.getTransaction().commit();


        Turn turn1 = new Turn(LocalDate.of(2021, Month.JULY, 20), diary1, turnStatus1, patient1);
        Turn turn4 = new Turn(LocalDate.of(2021, Month.JULY, 20), diary1, turnStatus2, patient1);
        Turn turn5 = new Turn(LocalDate.of(2021, Month.JULY, 20), diary1, turnStatus3, patient1);
        Turn turn6 = new Turn(LocalDate.of(2021, Month.JULY, 20), diary1, turnStatus1, patient1);
        Turn turn2 = new Turn(LocalDate.of(2020, Month.JULY, 20), diary2, turnStatus2, patient2);
        Turn turn3 = new Turn(LocalDate.of(2021, Month.JULY, 20), diary3, turnStatus3, patient3);

        em.getTransaction().begin();
        em.persist(turn1);
        em.persist(turn4);
        em.persist(turn5);
        em.persist(turn6);
        em.persist(turn2);
        em.persist(turn3);
        em.getTransaction().commit();


        System.out.println("Ex 01");

        TypedQuery<Turn> qryNome;
        qryNome = em.createQuery("from Turn where day = :pDate", Turn.class);
        qryNome.setParameter("pDate", LocalDate.of(2021, Month.JULY, 20));
		List<Turn> turns = qryNome.getResultList();

		List<Patient> patients = turns.stream()
                .map(Turn::getPatient)
                .collect(Collectors.toList());

        patients.forEach(p -> System.out.println(p.getName()));


        System.out.println("Ex 02");

        TypedQuery<Dentist> query = em.createQuery("select de from Dentist de inner join Diary di on(de.id = di.dentist) inner join Turn t on(t.diary = di.id) group by de.id, t.day having count(t.id) > 2", Dentist.class);
        List<Dentist> results = query.getResultList();

        results.forEach(r -> System.out.println(r.getName()));


        System.out.println("Ex 03");

        TypedQuery<Turn> qryDesc;
        qryDesc = em.createQuery("from Turn where turnstatus_id = :pStatus", Turn.class);
        qryDesc.setParameter("pStatus", 1);
		List<Turn> turns2 = qryDesc.getResultList();

        System.out.println(turns2);
        turns2.forEach(t -> System.out.println(t.getTurnStatus()));

        System.out.println("Ex 04");

        TypedQuery<Turn> qryDesc2;
        qryDesc2 = em.createQuery("from Turn where turnstatus_id = :pStatus and day = :pDay", Turn.class);
        qryDesc2.setParameter("pStatus", 2);
        qryDesc2.setParameter("pDay", LocalDate.of(2020, Month.JULY, 20));
		List<Turn> turns3 = qryDesc2.getResultList();

        System.out.println(turns3);
        turns3.forEach(t -> System.out.println(t.getTurnStatus()));


        System.out.println("Ex 05");

        TypedQuery<Diary> qryDesc3;
        qryDesc3 = em.createQuery("from Diary where dentist = :pDentist", Diary.class);
        qryDesc3.setParameter("pDentist", dentista1);
		List<Diary> diary = qryDesc3.getResultList();

        System.out.println(diary);

        System.out.println("OK");

        System.out.println("Ex 06");

        TypedQuery<Turn> qryDesc5;
        qryDesc5 = em.createQuery("select t from Dentist de inner join Diary di on(de.id = di.dentist) inner join Turn t on(t.diary = di.id) where de.id = :pDentistId and t.turnStatus.id = :pStatus", Turn.class);
        qryDesc5.setParameter("pDentistId", 3L);
        qryDesc5.setParameter("pStatus", 3L);
		List<Turn> turns10 = qryDesc5.getResultList();

        System.out.println("OK");
    }
}
