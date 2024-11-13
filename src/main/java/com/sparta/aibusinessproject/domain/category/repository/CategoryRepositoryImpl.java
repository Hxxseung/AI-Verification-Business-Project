//package com.sparta.aibusinessproject.domain.category.repository;
//
//import com.sparta.aibusinessproject.domain.category.dto.response.CategoryListResponse;
//import com.sparta.aibusinessproject.domain.category.entity.Category;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public class CategoryRepositoryImpl implements  CategoryRepositoryCustom{
//
//    private final JPAQueryFactory queryFactory;
//
//
//    @Override
//    public Page<CategoryListResponse> searchCategories(Pageable pageable) {
//        // 정렬 기준 설정
//        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);
//
//        QueryResults<Category> results = queryFactory
//                .selectFrom(category)
//                // 정렬
//                .orderBy(orders.toArray(new OrderSpecifier[0]))
//                // 페이징 처리
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                // 결과값 반환
//                .fetchResults();
//
//        List<CategoryListResponse> content = results.getResults().stream()
//                .map(c -> CategoryListResponse.from(c))
//                .collect(Collectors.toList());
//
//        long total = results.getTotal();
//
//        // PageImpl 객체를 생성하여 결과를 반환합니다. 이 객체는 페이지 내용, 페이지 정보, 총 결과 수를 포함
//        return new PageImpl<>(content, pageable, total);
//    }
//
//
//    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
//        List<OrderSpecifier<?>> orders = new ArrayList<>();
//
//        // pageable 객체에서 정렬 정보가 존재하는지 확인합니다. 만약 정렬정보가 존재한다면
//        if (pageable.getSort() != null) {
//            // pageable.getSort()로부터 반환된 Sort.Order 객체들을 반복
//            for (Sort.Order sortOrder : pageable.getSort()) {
//
//                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
//                switch (sortOrder.getProperty()) {
//                    case "createdAt":
//                        orders.add(new OrderSpecifier<>(direction, category.createdAt));
//                        break;
//                    case "modifiedAt":
//                        orders.add(new OrderSpecifier<>(direction, store.updatedAt));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//
//        return orders;
//    }
//}
