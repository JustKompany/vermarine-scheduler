FROM vertx/vertx3

ENV VERTICLE_NAME com.vermarine.VermarineServer
ENV VERTICLE_FILE build/classpath/

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

COPY $VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["vertx run $VERTICLE_NAME -cp $(ls -1 $VERTICLE_HOME/* | tr '\n' ':')"]