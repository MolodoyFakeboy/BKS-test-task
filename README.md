# BKS-test-task
Необходимо реализовать java-приложение для подсчета текущей стоимости портфеля (набора) акций и их распределение по секторам.

Посредством REST API на вход сервиса подается объект, представляющий собой произвольный набор акций с указанием количества каждой из них.

# Launch instructions 
1.Dowload my app from git

2.Build docker container: docker build -t bks-stoks .

3.Run docker container: docker run -p 8080:8080 bks-stoks

 # Endpoints
POST http://localhost:8080/portfolio/allocations
Входные данные
~~~
 {
    "stocks":[
      {
         "symbol":"AAPL",
         "volume":50
      },
      {
         "symbol":"HOG",
         "volume":10
      },
      {
         "symbol":"IDRA",
         "volume":1
      },
      {
         "symbol":"MRSN",
         "volume":1
      },
      {
         "symbol":"FST-NW",
         "volume":100 
      }
    ]
}
~~~

Результат
~~~
{
    "allocations": [
        {
            "sector": "Finance and Insurance",
            "assetValue": 6212.00,
            "proportion": 0.419
        },
        {
            "sector": "Manufacturing",
            "assetValue": 8602.91,
            "proportion": 0.581
        }
    ],
    "value": 14814.91
}
~~~

