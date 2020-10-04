# 注意，本构建文件仅限于maven构建使用，请勿徒手docker build，谢谢

FROM amazoncorretto:8
MAINTAINER weibocy weibocy@hotmail.com

# 国内debian源 centos镜像 此命令不适用
# ADD sources.list /etc/apt/sources.list

# 中文字体 centos镜像 此命令不适用
# ADD simsun.ttc /usr/share/fonts/simsun.ttc
# RUN apt-get update && apt-get install -y --no-install-recommends fonts-wqy-microhei fonts-wqy-zenhei xfonts-wqy xfonts-intl-chinese xfonts-intl-chinese-big ttf-dejavu fonts-arkpandora fonts-arphic-bkai00mp fonts-arphic-bsmi00lp fonts-arphic-gbsn00lp fonts-arphic-ukai fonts-arphic-uming fonts-cns11643-kai fonts-cns11643-pixmaps fonts-cns11643-sung latex-cjk-chinese-arphic-bkai00mp fonts-noto-cjk fonts-noto-cjk-extra && rm -rf /var/lib/apt/lists/*

# 匿名卷
VOLUME /tmp

# 时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 日志目录
RUN mkdir -p /logs/ && chown -R nobody:nobody /logs/

# 普通用户运行
USER nobody

# 添加服务jar包
ARG JAR_FILE
ADD --chown=nobody target/${JAR_FILE} /app.jar

# JVM参数
ENV JAVA_OPTS="-Xms256m -Xmx1024m -Xss512k"

# 环境配置
ENV RUN_ENV="test"

# 导出端口
EXPOSE 9055

# 启动命令
CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.profiles.active=$RUN_ENV