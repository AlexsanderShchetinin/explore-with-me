package ewm_stats_service.service;

import ewm_stats_dto.dto.EndpointHitDto;
import ewm_stats_service.model.EndpointHit;
import ewm_stats_service.repository.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import util.DataUtils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class StatisticServiceImplTest {

    private final StatisticRepository repository;
    private final StatisticServiceImpl service;
    private final EntityManager em;

    @Autowired
    StatisticServiceImplTest(StatisticRepository repository, StatisticServiceImpl service, EntityManager em) {
        this.repository = repository;
        this.service = service;
        this.em = em;
    }


    @BeforeEach
    void setUp(){
        // удаляем из БД все записи и добавляем одну по корневому uri
        repository.deleteAll();
        service.addHit(DataUtils.makeNewEndpointHitDto("/events"));
    }

    @Test
    @DisplayName(value = "тест сохранения EndpointHit в базу данных")
    void givenNewEndpointHitDto_whenAddHitToService_thenDBContainCorrectRecord() {
        // given
        EndpointHitDto endpointHitDto = DataUtils.makeNewEndpointHitDto("/events/999");

        // when
        service.addHit(endpointHitDto);

        // then
        // получаем кол-во записей в БД (должно быть две)
        TypedQuery<Long> queryCountAll = em.createQuery(
                "SELECT COUNT(*) " +
                        "FROM EndpointHit AS eh ", Long.class);
        long records = queryCountAll.getSingleResult();
        assertThat(records, equalTo(2L));

        // добавляем еще одну запись и проверяем еще раз по другим параметрам
        EndpointHitDto endpointHitDto2 = DataUtils.makeNewEndpointHitDto("/events/999");
        service.addHit(endpointHitDto2);

        TypedQuery<EndpointHit> query = em.createQuery("SELECT eh " +
                "FROM EndpointHit AS eh " +
                "WHERE eh.uri = :uri", EndpointHit.class);
        List<EndpointHit> endpointHits = query.setParameter("uri", "/events/999").getResultList();

        assertThat(endpointHits.size(), equalTo(2));

        TypedQuery<Long> queryCountAll2 = em.createQuery(
                "SELECT COUNT(*) " +
                        "FROM EndpointHit AS eh ", Long.class);
        assertThat(queryCountAll2.getSingleResult(), equalTo(3L));  // всего записей на одну больше

    }

}