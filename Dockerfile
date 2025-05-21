FROM 777706696655.dkr.ecr.ap-south-1.amazonaws.com/appdynamics:brmsbase-dynatrace-oneagent
RUN sh -x /opt/dynatrace/oneagent/dynatrace-agent64.sh
ENV LD_PRELOAD=/opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so
RUN export LD_PRELOAD=/opt/dynatrace/oneagent/agent/lib64/liboneagentproc.so
RUN echo $LD_PRELOAD
EXPOSE 8080 28017 28018 28019
ADD /target/location-0.0.1-SNAPSHOT.jar location-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","location-0.0.1-SNAPSHOT.jar"]