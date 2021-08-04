#동원디어푸드 개발 1팀 인턴 임소희

### PRODUCT RESTful API Reference

---

# 😇 INTRODUCTION

## 🔮 11st 판매/관리 서비스

- 상품 관리
- 상품 카테고리는 아래와 같이 Food 카테고리를 기준으로 대분류 중분류 소분류로 나눠짐.

  **괄호 안 숫자는 각 카테고리의 아이디**

  Food(1) - 전체 분류

  통조림/즉석/면류(2), 생수/음료/커피(12) - 대분류

  통조림/캔(3), 즉석밥/컵밥(6), 라면(9), 생수/탄산수(13), 음료/주스(16) - 중분류

  ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%86%E1%85%A9%E1%86%AF_%E1%84%8F%E1%85%A1%E1%84%90%E1%85%A6%E1%84%80%E1%85%A9%E1%84%85%E1%85%B5.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%86%E1%85%A9%E1%86%AF_%E1%84%8F%E1%85%A1%E1%84%90%E1%85%A6%E1%84%80%E1%85%A9%E1%84%85%E1%85%B5.png)

## 🛠 개발 환경

- Develop Language :  Java (JDK 1.8)
- Framework : Spring Boot/Gradle
- Persistence Framework  :  MyBatis
- DataBase : Mysql
- API DOCUMENT : Swagger
- API TEST TOOL : Post Man

---

# 🤨  MODELING & ERD

![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/ERD.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/ERD.png)

## 🍒 Category Table

- **계층형 구조  테이블** (아래 소스 코드 참고)
- 자식노드의 아이디가 부모노드의 아이디 참조.

```sql
create table category
(
    id        int unsigned auto_increment
        primary key,
    title     varchar(255) not null,
    parent_id int unsigned null,
    depth     int          not null,
    constraint category_ibfk_1
        foreign key (parent_id) references category (id)
            on update cascade on delete cascade
);

create index parent_id
    on category (parent_id);
```

- 프로젝트상 Mysql의 버전은 5.7 이기때문에 카테고리를 조회하는 경우 Self Join 으로 쿼리문 작성.

```sql
select
       c1.title as 'level1',
       c2.title as 'level2',
       c3.title as 'level3',
       c4.title as 'level4'
from
     category as c1
         left join category as c2 on c2.parent_id = c1.id
         left join category as c3 on c3.parent_id = c2.id
         left join category as c4 on c4.parent_id = c3.id
where c1.title = 'food';
```

- My Sql 8.x 버전 이상의 경우
  - with 임시 테이블 사용하여 재귀 쿼리 구현.

    ```sql
    with recursive category_path (id, title, path) AS (
        select id, title, title as path
        from category
        where parent_id is null
        union all
        select c.id, c.title, concat(cp.path, '>', c.title)
        from category_path as cp
                 join category as c on cp.id = c.parent_id
    )
    select *
    from category_path
    order by path;
    ```

## 🍊 Product Table

- Id  : AutoIncrement/PK
- category_id : FK
- 상품 판매 중지 경우 데이터를 DB에서 완전히 삭제 하는것 보다 `0 (Default value : 판매중 상품), 1(판매 중지 상품)`으로 표시하는 데이터 논리 삭제방법을 이용.
  - 물리적으로 데이터를 삭제하지 않기때문에 삭제되기 전의 상태로 간단히 되돌릴 수 있음.
  - **일시적인 재고 수량 문제로 고객들의 혼선을 방지하고자 임시로 판매중지를 하고 문제 해결 후 다시 판매 중인 상품으로 되돌리는 경우를 생각.**
  - **임시 판매중지 후 같은 상품을 다시 등록하는 번거로움이 없음.**
  - 논리 삭제는 추가적으로 주문 관련 통계를 낼 경우 유용하게 사용 할 수 있음.

```sql
create table product
(
    id           int auto_increment comment '상품 아이디'
        primary key,
    category_id  int unsigned                        null,
    product_name varchar(200)                        not null comment '상품 이름',
    price        varchar(50)                         null comment '상품가격',
    description  varchar(1000)                       null comment '상품 설명',
    content      text                                not null comment '상품상세설명',
    create_date  timestamp default CURRENT_TIMESTAMP null comment '상품 생성일',
    modify_date  timestamp default CURRENT_TIMESTAMP null,
    delete_flag  int       default 0                 not null
)
    comment '상품';

create index product_category_id_fk
    on product (category_id);
```

- 상품 조회 데이터를 조회하는 쿼리문은 inner join을 이용

    ```sql
    SELECT      p.id,
                        p.product_name as productName,
                        p.category_id as categoryId,
                        c.parent_id as parentId,
                        c.title as categoryName,
                        p.description as description,
                        p.content as content,
                        p.price as price,
                        p.delete_flag as deleteFlag,
                        fi.id as file_id,
                        fi.file_name as fileName,
                        pi.id as pi_id,
                        pi.delete_flag as pi_delete_flag,
                        p.create_date as createDate,
                        p.modify_date as modifyDate,
                        p.delete_flag as deleteFlag
                        FROM product p
            INNER JOIN category c on p.category_id = c.id
            INNER JOIN product_image pi on p.id = pi.product_id
            INNER JOIN file_info fi on pi.file_id = fi.id
            where p.category_id = :categoryId;
    ```

## 🍋 Product Image 와 File_info Table

- 확장성을 위해서 ProductImage와 FileInfo Tabale을 나누어 설계함.
- 이후에 추가 될 수 있는 SellerDisplayImage Table 등이 있음.
- 이미지 정보와 이미지 파일 정보를 따로 관리

```sql
create table file_info
(
    id             int auto_increment comment '파일 아이디'
        primary key,
    file_name      varchar(100)                        null comment '파일 이름',
    save_file_name varchar(100)                        null comment '파일 저장 이름',
    content_type   varchar(100)                        null,
    create_date    timestamp default CURRENT_TIMESTAMP null comment '생성일',
    modify_date    timestamp default CURRENT_TIMESTAMP null comment '수정일'
)
    comment '업로드 파일 정보';
```

```sql
create table product_image
(
    id          int auto_increment comment '상품 이미지 아이디'
        primary key,
    product_id  int                                 not null comment '상품 아이디',
    file_id     int                                 null comment '파일 아이디',
    delete_flag  int       default 0                 not null comment '이미지 파일 타입',
    create_date timestamp default CURRENT_TIMESTAMP null comment '상품이미지 생성일',
    constraint product_image_file_info_id_fk
        foreign key (file_id) references file_info (id),
    constraint product_image_product_id_fk_2
        foreign key (product_id) references product (id)
)
    comment '상품이미지';
```

---

# 🧸 API REFERENCE

[](http://localhost:8080/swagger-ui.html#/)

![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled.png)

Swagger Document

[요청 공통](https://www.notion.so/e65e09333549401b9992419a8dadc683)

[헤더](https://www.notion.so/d38260931949465b9b594eb8d6788d2d)

[응답 공통 HTTP 응답 코드](https://www.notion.so/7669ea45f79e42369e3aa309246b9798)

---

### 💜 11st API

![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%201.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%201.png)

- 11번가의 상품조회 API 호출

[개발가이드 | 11번가 OPEN API](https://openapi.11st.co.kr/openapi/OpenApiGuide.tmall)

- XML 타입으로 요청 및 응답을 받음
- XML 타입을 Json의 형태로 Parsing 후 응답.
- Api key 보안

  ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2021-08-04_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.31.30.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2021-08-04_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.31.30.png)

  application.propertise

  ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2021-08-04_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.08.32.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2021-08-04_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_11.08.32.png)

  ProductSearchImpl.java

- `RestTemplate` : 11번가 API 호출
- `XmlMapper` & `JacksonAnnotation` : Xml to Java Object
- Response Body (삼각형 눌러주세요🙇🏻‍♂️)

    ```json
    "products": {
        "totalCount": "477465",
        "product": [
          {
            "productCode": "2970367869",
            "productName": "[JDX] 제이디엑스 SS 시즌 깜짝인하! OPEN! 반팔/티셔츠/팬츠/큐롯/자켓/점퍼/골프웨어",
            "productPrice": "20000",
            "imageUrl": "http://i.011st.com/t/080/pd/21/3/6/7/8/6/9/McvHy/2970367869_L300.jpg",
            "sellerNick": "JDX_GOLF",
            "productDetailUrl": "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=2970367869",
            "seller": "jdxmultisports",
            "rating": "0",
            "salePrice": "15600",
            "delivery": "무료",
            "reviewCount": "6061",
            "buySatisfy": "93",
            "benefit": {
              "discount": "4400"
            }
          },
          {
            "productCode": "1013670781",
            "productName": "[업타운홀릭]~60%혜택! 홀릭MADE 여름신상 단독오픈! 입기만해도 부티나",
            "productPrice": "22000",
            "imageUrl": "http://i.011st.com/t/080/pd/21/6/7/0/7/8/1/FxINm/1013670781_L300.jpg",
            "sellerNick": "업타운홀릭_",
            "productDetailUrl": "http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=1013670781",
            "seller": "uptownholic1",
            "rating": "8293",
            "salePrice": "19800",
            "delivery": "무료",
            "reviewCount": "58703",
            "buySatisfy": "83",
            "benefit": {
              "discount": "2200"
            }
          },
    ...중략...
    ```

### 💜 CATEGORY API

![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%202.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%202.png)

- Response Body (삼각형 눌러주세요🙇🏻‍♂️)

    ```json
    [
      {
        "level1": "food",
        "level2": "통조림/즉석/면류",
        "level3": "통조림/캔",
        "level4": "참치/연어 통조림"
      },
      {
        "level1": "food",
        "level2": "통조림/즉석/면류",
        "level3": "통조림/캔",
        "level4": "고등어/꽁치 통조림"
      },
      {
        "level1": "food",
        "level2": "통조림/즉석/면류",
        "level3": "즉석밥/컵밥",
        "level4": "즉석밥"
      }
    ... 중략 ...
    ]
    ```

### 💜 PRODUCT API

![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%203.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%203.png)

1. 하위 카테고리 상품 조회
  - Request Parameter

    keyword : 하위카테고리 아이디(Default Value : 8)

  - Response Body

    `http://localhost:8080/api/v1/product/subcategory?keyword=8`

      ```json
      {
        "status": "success",
        "productList": [
          {
            "parentId": 6,
            "categoryId": 8,
            "categoryName": "컵밥",
            "id": 7,
            "productName": "[칼로리는 걱정NO, 배는 든든하게] 대단한 컵밥 4종 A세트/B세트 2종 택1 컵밥,냉동식품,도시락",
            "description": "든든한 대용량 한 끼 대단한 컵밥",
            "content": "대단한 컵밥 버라이어티 10종 구성 300g 대용량으로 든든하게 한 끼 하세요.",
            "price": "24500",
            "deleteFlag": "0",
            "createDate": "2021-08-03 16:37:47",
            "modifyDate": "2021-08-03 16:37:47",
            "productImage": [
              {
                "id": 7,
                "deleteFlag": "0",
                "fileInfo": {
                  "id": 7,
                  "fileName": "스크린샷 2021-07-29 오후 1.41.37.png"
                }
              }
            ]
          }
        ]
      }
      ```

  - Exception : NO_EXIST_CATEGORY_ID

    [`http://localhost:8080/api/v1/product/subcategory?keyword=100`](http://localhost:8080/api/v1/product/subcategory?keyword=100)

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%204.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%204.png)

2. 상품 등록
  - Request Parmeter

    categoryID : 하위 카테고리 아이디

    content : 상품 설명

    description : 상품 상세 설명

    price(String) : 상품 가격

    productImage : 상품 이미지 파일

    productName : 상품 이름

  - Response Body

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%205.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%205.png)

3. 상품 가격 수정
  - Request Parmeter

    modifyPrice : 수정 가격

    productId : 수정할 상품 아이디

  - Response Body

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%206.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%206.png)

  - Exception : NO_EXIST_ID

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%207.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%207.png)

4. 상품 판매 중지
  - Request Parmeter

    productId : 판매중지할 상품 아이디

  - Response Body

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%208.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%208.png)

  - Exception : NO_EXIST_ID

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%209.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%209.png)

  - Exception : ALREADY_SUSPEND_STATUS

    ![PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%2010.png](PRODUCT%20RESTful%20API%20Reference%2063f9f224daed42528d51722ca07e0fb9/Untitled%2010.png)

---

Copyright © Sophie . All rights reserved.
