package ewm_stats_service.repository;

import ewm_stats_service.model.EndpointHit;
import ewm_stats_service.model.ViewStatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT COUNT(*) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND eh.uri=:uri " +
            "AND eh.app=:app ")
    int countHitsByUriWithoutUniqueIp(LocalDateTime start, LocalDateTime end, String uri, String app);

    @Query("SELECT COUNT(DISTINCT ip) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :start AND :end " +
            "AND eh.uri=:uri " +
            "AND eh.app=:app ")
    int countHitsByUriWithUniqueIp(LocalDateTime start, LocalDateTime end, String uri, String app);


    @Query(value = "SELECT application AS app, uri AS uri, COUNT(uri) AS hits " +
            "FROM endpoint_hits AS eh " +
            "WHERE eh.timestamp_request BETWEEN :start AND :end " +
            "AND eh.application= :app " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC", nativeQuery = true)
    List<ViewStatsProjection> findAllStatsWithoutUniqueIpOrderByHitsDesc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("app") String app);


    @Query(value = "SELECT application AS app, uri AS uri, COUNT(DISTINCT(ip)) AS hits " +
            "FROM endpoint_hits AS eh " +
            "WHERE eh.timestamp_request BETWEEN :start AND :end " +
            "AND eh.application=:app " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC ", nativeQuery = true)
    List<ViewStatsProjection> findAllStatsWithUniqueIpOrderByHitsDesc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("app") String app);


}
