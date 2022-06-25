# **************************************************************************** #
#                                                                              #
#                                                         :::      ::::::::    #
#    Makefile                                           :+:      :+:    :+:    #
#                                                     +:+ +:+         +:+      #
#    By: amalliar <marvin@42.fr>                    +#+  +:+       +#+         #
#                                                 +#+#+#+#+#+   +#+            #
#    Created: 2022/06/25 12:44:32 by amalliar          #+#    #+#              #
#    Updated: 2022/06/25 18:01:47 by amalliar         ###   ########.fr        #
#                                                                              #
# **************************************************************************** #

JC            := javac
SOURCES_DIR   := ./src/java
SOURCES       := $(shell find -type f -name '*.java')
CLASSES_DIR   := ./target
RESOURCES_DIR := ../src/resources
CLASSES       := $(SOURCES:$(SOURCES_DIR)/%.java=$(CLASSES_DIR)/%.class)
ENTRYPOINT    := com.school21.printer.app.Program
JAR           := images-to-char.jar

all: $(CLASSES)
$(CLASSES): $(SOURCES)
	@$(JC) -verbose -d $(CLASSES_DIR) $^
.PHONY: all

clean:
	@rm -rf $(CLASSES_DIR)
.PHONY: clean

re:
	@$(MAKE) clean
	@$(MAKE) all
.PHONY: re

jar: $(CLASSES_DIR)/$(JAR)
$(CLASSES_DIR)/$(JAR): $(CLASSES)
	@cd $(CLASSES_DIR) && jar cfve $(JAR) $(ENTRYPOINT) . $(RESOURCES_DIR)
	@echo "start the application by issuing: java -jar target/$(JAR)" 
.PHONY: jar

help:
	@echo "The following are some of the valid targets for this Makefile:"
	@echo "... all (the default if no target is provided)"
	@echo "... clean"
	@echo "... re"
	@echo "... jar"
.PHONY: help
