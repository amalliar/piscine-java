# **************************************************************************** #
#                                                                              #
#                                                         :::      ::::::::    #
#    Makefile                                           :+:      :+:    :+:    #
#                                                     +:+ +:+         +:+      #
#    By: amalliar <marvin@42.fr>                    +#+  +:+       +#+         #
#                                                 +#+#+#+#+#+   +#+            #
#    Created: 2022/06/25 12:44:32 by amalliar          #+#    #+#              #
#    Updated: 2022/06/25 16:50:52 by amalliar         ###   ########.fr        #
#                                                                              #
# **************************************************************************** #

JC          := javac
SOURCES_DIR := ./src/java
SOURCES     := $(shell find -type f -name '*.java')
CLASSES_DIR := ./target
CLASSES     := $(SOURCES:$(SOURCES_DIR)/%.java=$(CLASSES_DIR)/%.class)

pxWhiteChar ?= ░
PX_WHITE_CHAR := --pxWhiteChar=$(pxWhiteChar)

pxBlackChar ?= █
PX_BLACK_CHAR := --pxBlackChar=$(pxBlackChar)

IMAGE_FILE  := --imageFile=$(shell readlink -f $(imageFile) 2>/dev/null)
ARGS := $(IMAGE_FILE) $(PX_WHITE_CHAR) $(PX_BLACK_CHAR)

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

run: all
	java -cp $(CLASSES_DIR) com.school21.printer.app.Program $(ARGS)
.PHONY: run

help:
	@echo "The following are some of the valid targets for this Makefile:"
	@echo "... all (the default if no target is provided)"
	@echo "... clean"
	@echo "... re"
	@echo "... run"
.PHONY: help
