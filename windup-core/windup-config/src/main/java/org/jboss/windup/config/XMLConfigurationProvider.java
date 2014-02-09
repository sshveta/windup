/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.windup.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jboss.windup.config.parser.ParserContext;
import org.jboss.windup.graph.GraphContext;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.ConfigurationProvider;
import org.w3c.dom.Document;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class XMLConfigurationProvider implements ConfigurationProvider<GraphContext>
{

   @Override
   public int priority()
   {
      return 0;
   }

   @Override
   public boolean handles(Object payload)
   {
      return payload instanceof GraphContext;
   }

   @Override
   public Configuration getConfiguration(GraphContext context)
   {
      try
      {
         ClassLoader classloader = this.getClass().getClassLoader();
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         dbFactory.setNamespaceAware(true);
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(classloader.getResourceAsStream("windup-rewrite-xml-config.xml"));

         ConfigurationBuilder builder = ConfigurationBuilder.begin();
         ParserContext parser = new ParserContext(builder);

         parser.processElement(doc.getDocumentElement());

         return builder;
      }
      catch (Exception e)
      {
         throw new RuntimeException("Failed to parse XML configuration (better message please)", e);
      }
   }

}
