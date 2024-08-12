package cloud.popples.buildweb.spittr.repository;

import cloud.popples.buildweb.spittr.pojo.Spittle;

import java.util.List;

public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);

    Spittle findOne(long id);
}
