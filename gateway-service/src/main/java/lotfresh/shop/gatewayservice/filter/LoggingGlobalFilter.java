//package lotfresh.shop.gatewayservice.filter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class LoggingGlobalFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//
//        // 로깅 로직
//        System.out.println("Request: " + request.getURI());
//        System.out.println("Response Status: " + response.getStatusCode());
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        // 필터의 우선순위를 결정합니다. 낮은 값이 우선순위가 높습니다.
//        return -1;
//    }
//}