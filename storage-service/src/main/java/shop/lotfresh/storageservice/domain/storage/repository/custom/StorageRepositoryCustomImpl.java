package shop.lotfresh.storageservice.domain.storage.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StorageRepositoryCustomImpl implements StorageRepositoryCustom {

    private final JPAQueryFactory query;

//    @Override
//    public Optional<Storage> findByIdFetch(Long id) {
///*        return Optional.ofNullable(
//                query
//                        .selectFrom(product)
//                        .join(product.category)
//                        .fetchJoin()
//                        .where(product.id.eq(id))
//                        .fetchOne());
//    }*/
//        return true;
//    }

}