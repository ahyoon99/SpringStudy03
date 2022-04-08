package com.example.client.service;

import com.example.client.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    // http://localhost/api/server/hello
    // 에 요청을 해서 response를 받아올 것이다.
    public UserResponse hello() {
        // 우선 주소를 만들어보겠다. 주소를 만드는데 많이 사용하는 것이 uri component builder라는 것이 있다.
        // get 주소를 만들때 query parameter가 들어가야할때는 queryParam을 사용하여 만들어주면 된다.
        // queryParam 사용했을 때의 주소 : http://localhost:9090/api/server/hello?name=steve&age=10
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name","aaaa")
                .queryParam("age",99)
                .encode()   // 원래는 encode 안해도 되지만, 만약 파라미터가 붙을 때에는 안정적으로 url encoding을 해서 보내야하기 때문에 encode를 해주겠다.
                .build()
                .toUri();
        System.out.println(uri.toString()); // 주소가 잘 만들어졌는지 확인해주기

        // 위의 코드를 통해 주소에 해당하는 uri를 만들어주었다.
        // 이 uri를 restTemplate을 통해서 통신을 할 것이다.
        RestTemplate restTemplate = new RestTemplate();
        // 1. getForObject 사용 : 두번째 파라미터 값인 String.class 타입이 리턴된다.
//        String result = restTemplate.getForObject(uri, String.class);   // String.class 타입으로 리턴값을 받아보겠다.
//                                                                        // getForObject()가 실행되는 순간이 client에서 http 서버로 붙는 순간이다.
//                                                                        // getForObject에서의 get은 가져오겠다의 get이 아니라 http 메소드의 get을 의미한다.
//        return result;

        //  2. getForEntity 사용 : ResponseEntity<T> 타입이 리턴된다.
//        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);   // result에 여러가지 정보가 담겨있다.
//        System.out.println(result.getStatusCode()); // http의 status code를 볼 수 있다. // 200 OK 가 출려된다.
//        System.out.println(result.getBody());   // response에 들어있는 내용이 들어있다. // hello server 가 출력된다.
//
//        return result.getBody();

        // 3. json 형식으로 response 받기
        // header내용 등 상세한 내용을 알기위해서는 getForEntity를 사용하는 것이 좋다.
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();    // result.getBody() 안에는 UserResponse가 들어있다. -> return값 UserResponse로 바꿔주기

    }
}

