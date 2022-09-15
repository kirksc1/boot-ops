package com.github.kirksc1.bootops.cli;

import com.github.kirksc1.bootops.core.ItemManifestStreamFactory;
import com.github.kirksc1.bootops.core.ItemStreamCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The BootOpsApplicationRunner is the ApplicationRunner that initiates the business logic
 * for the command line execution.
 */
@Slf4j
public class BootOpsApplicationRunner implements ApplicationRunner {

    public static final String COMMAND_NAME = "command";

    private final ItemStreamCommandService service;
    private final ItemManifestStreamFactory factory;

    /**
     * Construct a new BootOpsApplicationRunner with the provided details.
     * @param service The ItemStreamCommandService that will perform the command execution.
     * @param factory The factory to construct Item manifest streams.
     */
    public BootOpsApplicationRunner(ItemStreamCommandService service, ItemManifestStreamFactory factory) {
        Assert.notNull(service, "The ItemStreamCommandService provided was null");
        Assert.notNull(factory, "The ItemManifestStreamFactory provided was null");

        this.service = service;
        this.factory = factory;
    }

    /**
     * Execute the application's business process.
     * @param args The command line parameters from application initiation.
     * @throws Exception if a failure occurs within the application.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initiating BootOps application runner");

        if (args.containsOption(COMMAND_NAME)) {
            List<String> commandNames = args.getOptionValues(COMMAND_NAME);
            if (log.isDebugEnabled()) {
                commandNames.forEach(s -> log.debug("Found commands {}", commandNames));
            }

            Map<String,List<String>> params = buildParameterMap(args);
            if (log.isDebugEnabled()) {
                params.keySet().forEach(s -> log.debug("Found parameter {}={}", s, params.get(s)));
            }

            commandNames.forEach(s -> service.execute(s,params, factory));
        } else {
            throw new IllegalStateException("No command provided");
        }
    }

    /**
     * Construct a parameter Map from the provided ApplicationArguments.
     * @param args The command line parameters from application initiation.
     * @return The constructed parameter Map.
     */
    private Map<String, List<String>> buildParameterMap(ApplicationArguments args) {
        Map<String,List<String>> retVal = new HashMap<>();

        for (String name : args.getOptionNames()) {
            retVal.put(name, args.getOptionValues(name));
        }

        return retVal;
    }

}
