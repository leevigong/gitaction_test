package KKSC.page.domain.notice.repository.impl;

import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.repository.NoticeBoardRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static KKSC.page.domain.notice.entity.QNoticeBoard.noticeBoard;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeBoardRepositoryImpl implements NoticeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NoticeBoardListResponse> loadNoticeBoardListByKeyword(Keyword keyword, String query, Pageable pageable) {
        List<NoticeBoardListResponse> result = queryFactory
                .select(Projections.constructor(NoticeBoardListResponse.class,
                        noticeBoard.title,
                        noticeBoard.memberName.as("createdBy"),
                        noticeBoard.fileYN,
                        noticeBoard.view,
                        noticeBoard.delYN,
                        noticeBoard.createdAt
                )).from(noticeBoard)
                .where(
                        noticeBoard.delYN.eq(0L),
                        getKeywordQuery(keyword, query)
                ).orderBy(Objects.requireNonNull(getOrderSpecifier(pageable)).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = result.size();

        return new PageImpl<>(result, pageable, totalCount);
    }

    @Override
    public Page<NoticeBoardListResponse> loadNoticeBoardList(Pageable pageable) {
        List<NoticeBoardListResponse> result = queryFactory
                .select(Projections.constructor(NoticeBoardListResponse.class,
                        noticeBoard.title,
                        noticeBoard.memberName.as("createdBy"),
                        noticeBoard.fileYN,
                        noticeBoard.view,
                        noticeBoard.delYN,
                        noticeBoard.createdAt
                ))
                .from(noticeBoard)
                .where(noticeBoard.delYN.eq(0L))
                .orderBy(Objects.requireNonNull(getOrderSpecifier(pageable)).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = result.size();

        return new PageImpl<>(result, pageable, totalCount);
    }

    private BooleanExpression getKeywordQuery(Keyword keyword, String query) {
        return switch (keyword) {
            case TITLE -> noticeBoard.title.contains(query);
            case CONTENT -> noticeBoard.content.contains(query);
            case TITLE_CONTENT -> (noticeBoard.title.contains(query)).or(noticeBoard.content.contains(query));
            case CREATED_BY -> noticeBoard.memberName.contains(query);
        };
    }

    private List<OrderSpecifier<?>> getOrderSpecifier(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort().isEmpty()) return orders;

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()) {
                case "VIEW" -> {
                    orders.add(new OrderSpecifier<>(direction, noticeBoard.view));
                }
                default -> {
                    orders.add(new OrderSpecifier<>(direction, noticeBoard.createdAt));
                }
            }
        }

        return orders;
    }
}
