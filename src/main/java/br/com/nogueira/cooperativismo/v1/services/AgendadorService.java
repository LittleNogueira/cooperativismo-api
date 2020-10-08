package br.com.nogueira.cooperativismo.v1.services;

import br.com.nogueira.cooperativismo.v1.jobs.ResultadoJob;
import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;

@Service
public class AgendadorService {

     public void agendarJob(LocalDateTime dataHoraJob, Class<? extends Job> jobClass){
        Cron cron = gerarCron(dataHoraJob);
        SchedulerFactory shedFact = new StdSchedulerFactory();
        try {
            Scheduler scheduler = shedFact.getScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(jobClass)
                    .withIdentity("resultadoJOB", "grupo01")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("validadorTRIGGER","grupo01")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron.asString()))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private Cron gerarCron(LocalDateTime dataHoraJob){
        CronBuilder quartzCronBuilder = CronBuilder
                .cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));

        Cron cron = quartzCronBuilder.withSecond(on(dataHoraJob.getSecond()))
                .withDoM(on(dataHoraJob.getDayOfMonth())).withDoW(questionMark())
                .withHour(on(dataHoraJob.getHour())).withMinute(on(dataHoraJob.getMinute()))
                .withMonth(on(dataHoraJob.getMonthValue())).withYear(on(dataHoraJob.getYear())).instance();

        return cron;
    }
}
