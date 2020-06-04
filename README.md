


Docker:

    1. 项目根目录执行: mvn clean install -U -e -Dmaven.test.skip=true
    2. 进入到portal目录执行: mvn package docker:build
    3. 执行docker image ls 找到:heroland/heroland 
    4. 执行docker run -p 8080:8080 -t heroland/heroland