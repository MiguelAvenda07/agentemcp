package com.udea.agentemcp;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import java.time.Duration;
import java.util.List;

public class PedidoAgent {

    public static BaseAgent ROOT_AGENT = buildAgent();

    private static BaseAgent buildAgent() {
        StreamableHttpServerParameters params =
            StreamableHttpServerParameters.builder()
                .url("http://localhost:8088/mcp")
                .timeout(Duration.ofSeconds(30))
                .build();

        McpToolset mcpToolset = new McpToolset(params);

        try {
            List<BaseTool> mcpTools = mcpToolset.getTools(null)
                .toList()
                .blockingGet();

            System.out.println("Herramientas MCP descubiertas: " + mcpTools.size());

            return LlmAgent.builder()
                .name("PedidoAgent")
                .model("gemini-3.8-flash")
                .description("Asistente de pedidos de un supermercado a domicilio")
                .instruction(
                    "Eres el asistente de pedidos de un supermercado a domicilio. " +
                    "Usa las herramientas MCP para buscar productos, crear pedidos, " +
                    "agregar productos, consultar pedidos, cambiar su estado y cancelarlos. " +
                    "Antes de agregar un producto, búscalo con buscarProductos para obtener su id. " +
                    "Para pasar un pedido a EN_CAMINO o ENTREGADO usa actualizarEstadoPedido. " +
                    "Antes de cancelar un pedido, pide confirmación explícita al cliente. " +
                    "Responde en español, de forma breve y clara.")
                .tools(mcpTools)
                .build();

        } catch (Exception e) {
            throw new IllegalStateException(
                "No fue posible conectar con el servidor MCP en http://localhost:8088/mcp", e);
        }
    }
}