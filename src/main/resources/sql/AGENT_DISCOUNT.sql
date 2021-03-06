
  CREATE TABLE "TRADEOL"."AGENT_DISCOUNT" 
   ( "ID" NVARCHAR2(60) NOT NULL ENABLE, 
	"AGENCY_ID" NVARCHAR2(60), 
	"DISCOUNT" NUMBER(3,0), 
	"LINE_ID" NVARCHAR2(60), 
	"DESCRIPTION" NVARCHAR2(255), 

	 CONSTRAINT "ID_AGENT_DISCOUNT" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SM_SYSTEMSMARTAPP"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 196608 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SM_AGREEMENT" ;