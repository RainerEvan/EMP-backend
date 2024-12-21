package com.emp.backend.util;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

public class ReportEngine {

    public byte[] generatePdfReport(String reportDesignPath, Map<String, Object> parameters) throws EngineException{
        IReportEngine engine = null;
        EngineConfig config = null;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            config = new EngineConfig();
            Platform.startup(config);
            final IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = factory.createReportEngine(config);

            IReportRunnable reportRunnable = null;
            reportRunnable = engine.openReportDesign(reportDesignPath);
            IRunAndRenderTask task = engine.createRunAndRenderTask(reportRunnable);

            if (parameters != null) {
                parameters.forEach(task::setParameterValue);
            }

            PDFRenderOption pdfOptions = new PDFRenderOption();
            pdfOptions.setOutputFormat(PDFRenderOption.OUTPUT_FORMAT_PDF);
            pdfOptions.setOutputStream(outputStream);

            task.setRenderOption(pdfOptions);

            task.run();
            task.close();
            engine.destroy();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report", e);
        } finally {
            Platform.shutdown();
        }
    }
}
