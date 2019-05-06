package controller;

import model.entity.*;
import model.entity.Process;
import model.service.ProcessManager;
import model.service.ProductManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by amir on 7/3/17.
 */
@Controller
@RequestMapping("/document/process")
public class ProcessController {
    @Autowired
    private ProcessManager processManager;

    private static Logger logger = Logger.getLogger(ProcessController.class.getName());

    @RequestMapping("/register.do")
    public void register(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        JSONObject processProductObject = null;
        try {
            processProductObject = (JSONObject) new JSONParser().parse(data);
        } catch (ParseException e) {
            logger.error("Cant parse", e);
            return;
        }

        ProcessProduct processProduct = new ProcessProduct();
        try {
            processProduct.setCode((String) processProductObject.get("code"));
            processProduct.setName((String) processProductObject.get("name"));
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        
        JSONArray processArray = (JSONArray) processProductObject.get("machines");
        for (Object o : processArray) {
            JSONObject processObject = (JSONObject) o;

            Process process = new Process();
            process.setSectionCode((String) processObject.get("sectionCode"));
            process.setGroupCode((String) processObject.get("groupCode"));
            process.setSectionName((String) processObject.get("sectionName"));
            process.setGroupName((String) processObject.get("groupName"));
            process.setOutputCode((String) processObject.get("exitCode"));
            process.setOutputName((String) processObject.get("exitName"));
            process.setTotalOutput(Double.valueOf((String) processObject.get("exitTotal")));

            JSONArray inputMachineArray = (JSONArray) processObject.get("enterMachines");
            for (Object o1 : inputMachineArray) {
                JSONObject inputObject = (JSONObject) o1;
                boolean check = false;
                for (ProcessMachine processMachine : process.getInputMachines()) {
                    if ((processMachine.getSectionCode() + ":" + processMachine.getGroupCode()).equals((String) inputObject.get("sectionCode") + (String) inputObject.get("groupCode"))){
                        check = true;
                        ProcessSemi processSemi = new ProcessSemi();
                        processSemi.setSemiCode((String) inputObject.get("exitCode"));
                        processSemi.setSemiName((String) inputObject.get("exitName"));
                        processSemi.setTotal(Double.valueOf((String) inputObject.get("exitTotal")));
                        processMachine.getProcessSemis().add(processSemi);
                        break;
                    }
                }
                if (!check){
                    ProcessMachine processMachine = new ProcessMachine();
                    processMachine.setSectionCode((String) inputObject.get("sectionCode"));
                    processMachine.setGroupCode((String) inputObject.get("groupCode"));
                    processMachine.setSectionName((String) inputObject.get("sectionName"));
                    processMachine.setGroupName((String) inputObject.get("groupName"));

                    ProcessSemi processSemi = new ProcessSemi();
                    processSemi.setSemiCode((String) inputObject.get("exitCode"));
                    processSemi.setSemiName((String) inputObject.get("exitName"));
                    processSemi.setTotal(Double.valueOf((String) inputObject.get("exitTotal")));

                    processMachine.getProcessSemis().add(processSemi);

                    process.getInputMachines().add(processMachine);
                }
            }

            JSONArray outputMachineArray = (JSONArray) processObject.get("exitMachines");
            for (Object o1 : outputMachineArray) {
                JSONObject outputObject = (JSONObject) o1;
                boolean check = false;
                for (ProcessMachine processMachine : process.getOutputMachines()) {
                    if ((processMachine.getSectionCode() + ":" + processMachine.getGroupCode()).equals((String) outputObject.get("sectionCode") + (String) outputObject.get("groupCode"))){
                        check = true;
                        ProcessSemi processSemi = new ProcessSemi();
                        processSemi.setSemiCode(process.getOutputCode());
                        processSemi.setSemiName(process.getOutputName());
                        processSemi.setTotal(Double.valueOf((String) outputObject.get("exitTotal")));
                        processMachine.getProcessSemis().add(processSemi);
                        break;
                    }
                }
                if (!check){
                    ProcessMachine processMachine = new ProcessMachine();
                    processMachine.setSectionCode((String) outputObject.get("sectionCode"));
                    processMachine.setGroupCode((String) outputObject.get("groupCode"));
                    processMachine.setSectionName((String) outputObject.get("sectionName"));
                    processMachine.setGroupName((String) outputObject.get("groupName"));

                    ProcessSemi processSemi = new ProcessSemi();
                    processSemi.setSemiCode(process.getOutputCode());
                    processSemi.setSemiName(process.getOutputName());
                    processSemi.setTotal(Double.valueOf((String) outputObject.get("exitTotal")));

                    processMachine.getProcessSemis().add(processSemi);

                    process.getOutputMachines().add(processMachine);
                }
            }

            JSONArray rawMaterialArray = (JSONArray) processObject.get("rawMaterials");
            for (Object o1 : rawMaterialArray) {
                JSONObject rawMaterialObject = (JSONObject) o1;

                ProcessRaw processRaw = new ProcessRaw();
                processRaw.setRawCode((String) rawMaterialObject.get("code"));
                processRaw.setRawName((String) rawMaterialObject.get("name"));
                processRaw.setTotal(Double.valueOf((String) rawMaterialObject.get("total")));

                process.getRawInputsRaw().add(processRaw);
            }

            processProduct.getProcesses().add(process);
        }

        processManager.addProcess(processProduct);
    }

    @RequestMapping("/show.do")
    public void show(HttpServletRequest request, HttpServletResponse response){
        ProcessProduct processProduct = new ProcessProduct();
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(request.getParameter("data"));
            if (jsonObject == null){
                response.setStatus(400);
                return;
            }
            processProduct.setCode((String) jsonObject.get("code"));
            if (processProduct.getCode().trim().equals("") || processProduct.getCode() == null){
                response.setStatus(400);
                return;
            }
        }catch (Exception e){
            response.setStatus(400);
            return;
        }
        processProduct = processManager.getProcess(processProduct);
        if(processProduct == null){
            response.setStatus(404);
            return;
        }
        JSONArray edgesArray = new JSONArray();
        JSONArray nodesProductArray = new JSONArray();
        JSONArray nodesMachineArray = new JSONArray();

        for (Process process : processProduct.getProcesses()) {
            JSONObject nodeObject = new JSONObject();

            JSONObject machineObject = new JSONObject();
            machineObject.put("id", process.getId());
            machineObject.put("codeS", process.getSectionCode());
            machineObject.put("codeG", process.getGroupCode());
            machineObject.put("nameS", process.getSectionName());
            machineObject.put("nameG", process.getGroupName());
            machineObject.put("name", process.getSectionName() + ":" +  process.getGroupName());
            machineObject.put("type", "machine");
            nodeObject.put("data", machineObject);
            nodesMachineArray.add(nodeObject);

            nodeObject = new JSONObject();
            JSONObject productObject = new JSONObject();
            productObject.put("id", process.getId() + "p");
            productObject.put("code", process.getOutputCode());
            productObject.put("name", process.getOutputName());
            productObject.put("parent", process.getId());
            productObject.put("type", "product");
            productObject.put("value", process.getTotalOutput());
            nodeObject.put("data", productObject);
            nodesProductArray.add(nodeObject);
        }

        for (Process process : processProduct.getProcesses()) {
            for (ProcessMachine processMachine : process.getInputMachines()) {
                for (ProcessSemi processSemi : processMachine.getProcessSemis()) {
                    for (Object o : nodesProductArray) {
                        JSONObject jsonObject = (JSONObject) o;
                        jsonObject = (JSONObject) jsonObject.get("data");
                        if (jsonObject.get("code").equals(processSemi.getSemiCode()) && jsonObject.get("name").equals(processSemi.getSemiName())){
                            JSONObject edgeObject = new JSONObject();
                            JSONObject nodeObject = new JSONObject();
                            edgeObject.put("id", jsonObject.get("id") + "." + process.getId());
                            edgeObject.put("source", jsonObject.get("id"));
                            edgeObject.put("target", process.getId());
                            edgeObject.put("label", processSemi.getTotal());
                            nodeObject.put("data", edgeObject);
                            edgesArray.add(nodeObject);
                        }
                    }
                }
            }
            for (ProcessRaw processRaw : process.getRawInputsRaw()) {
                JSONObject nodeObject = new JSONObject();
                JSONObject productObject = new JSONObject();
                productObject.put("id", processRaw.getId());
                productObject.put("code", processRaw.getRawCode());
                productObject.put("name", processRaw.getRawName());
                productObject.put("type", "raw");
                nodeObject.put("data", productObject);
                nodesProductArray.add(nodeObject);
                JSONObject edgeObject = new JSONObject();
                nodeObject = new JSONObject();
                edgeObject.put("id", processRaw.getId() + "." + process.getId());
                edgeObject.put("source", processRaw.getId());
                edgeObject.put("target", process.getId());
                edgeObject.put("label", processRaw.getTotal());
                nodeObject.put("data", edgeObject);
                edgesArray.add(nodeObject);
            }
        }

        for (Object o : nodesMachineArray) {
            JSONObject jsonObject = (JSONObject) o;
            nodesProductArray.add(jsonObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodes", nodesProductArray);
        jsonObject.put("edges", edgesArray);

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            logger.error("resp Cant write", e);
            response.setStatus(500);
        }
    }
}
