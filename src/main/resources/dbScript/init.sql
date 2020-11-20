GO
USE [HaoWu]
GO
/****** Object:  Table [dbo].[CombinedQuestion]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CombinedQuestion](
	[ID] [varchar](128) NOT NULL,
	[TopCategoryID] [varchar](128) NOT NULL,
	[HotWordID] [varchar](128) NOT NULL,
	[URL] [nvarchar](500) NOT NULL,
	[Name] [nvarchar](500) NULL,
	[CreateTime] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[HotWord]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[HotWord](
	[ID] [varchar](128) NOT NULL,
	[TopCategoryID] [varchar](128) NOT NULL,
	[Name] [nvarchar](500) NOT NULL,
	[isDoneBaidu] [bit] NULL,
	[isDoneZhihu] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Question]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Question](
	[ID] [varchar](128) NOT NULL,
	[HotWordID] [varchar](128) NOT NULL,
	[URL] [nvarchar](500) NULL,
	[Source] [nvarchar](100) NULL,
	[Name] [nvarchar](500) NULL,
	[CreateTime] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[QuestionContent]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[QuestionContent](
	[ID] [varchar](128) NOT NULL,
	[QuestionID] [varchar](128) NOT NULL,
	[FollowerCount] [bigint] NULL,
	[BrowserCount] [bigint] NULL,
	[CreateTime] [datetime] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TopCategory]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TopCategory](
	[ID] [varchar](128) NOT NULL,
	[Name] [nvarchar](200) NOT NULL,
	[IsActive] [bit] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[XZSE86]    Script Date: 2020-11-20 18:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[XZSE86](
	[ID] [varchar](128) NOT NULL,
	[TopCategoryID] [varchar](128) NULL,
	[XZSE86JSON] [nvarchar](2000) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'40cbd298-9e95-4e4e-8fe8-25c8e899a584', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/21958857', NULL, CAST(0x0000AC7901316793 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'07f3ff01-cec5-4689-852d-4cf17f35b644', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/22726851', NULL, CAST(0x0000AC79013167BD AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'd46a6da0-e7f0-4984-bc1d-d5a2eb7267a8', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/26542497', NULL, CAST(0x0000AC79013167BF AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'0d6ba321-fe2f-4898-9d8d-644cab939011', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524', NULL, CAST(0x0000AC79013167C4 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'8b978951-e6da-4e28-876f-463725a6ebb2', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524?sort=created', NULL, CAST(0x0000AC79013167C5 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'e4d65f4c-9954-4f37-9770-bd14ec784f85', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/27531400', NULL, CAST(0x0000AC79013167C6 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'5d02d7ab-0c26-46f9-9f72-979025e138bd', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/276274224', NULL, CAST(0x0000AC79013167C8 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'0d95ceef-e1b4-474e-85f8-0ab82447bbfa', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/28349849', NULL, CAST(0x0000AC79013167C9 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'a61f74a8-7c3b-4aba-a87f-e6fdbc6de0f9', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/284812765', NULL, CAST(0x0000AC79013167CA AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'82c2a219-1d21-4999-8581-1d1e654c3eaf', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/293511214', NULL, CAST(0x0000AC79013167CC AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'd59aa287-0287-4101-bb64-621937074629', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/372434384', NULL, CAST(0x0000AC79013167CE AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'9f2516dc-a6ac-4368-ba25-59f504621a1a', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/37287487', NULL, CAST(0x0000AC79013167D0 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'1969263a-d202-4f9e-9ddd-c15d3efc26bd', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/393480397', NULL, CAST(0x0000AC79013167D1 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'b4f760cf-f3a0-4de4-8901-af8d3279c292', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/412170551', NULL, CAST(0x0000AC79013167D3 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'98f86210-583a-4b00-bdf6-35deb3edb441', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/422510526', NULL, CAST(0x0000AC79013167D4 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'72c33fb1-a8a6-43f7-85d7-4016e99b3caf', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/428054747', NULL, CAST(0x0000AC79013167D6 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'2477328b-c2f1-49fe-b169-c2849974977e', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/57662233', NULL, CAST(0x0000AC79013167D9 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'2b916457-beac-444e-8cc8-7bf15d72a799', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/61048115', NULL, CAST(0x0000AC79013167DB AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'192c8499-989a-4c81-b20e-27c57bfefdc2', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/20707674', NULL, CAST(0x0000AC79013167DC AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'e292d0d0-1608-4ec2-b9bc-7e96cef8de04', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/21416593', NULL, CAST(0x0000AC79013167DF AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'f2f474c6-f87c-4e3c-99db-8f2c7ad1541c', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/22726851', NULL, CAST(0x0000AC79013167E1 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'3ca84a48-a5fa-483b-b3e0-56ba11c49bcb', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/23403081', NULL, CAST(0x0000AC79013167E4 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'6549e618-d386-4304-ab45-b768a8994959', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/27531400', NULL, CAST(0x0000AC79013167E6 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'5d15c7d2-165d-417d-be1e-1f2dce792084', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/293692201', NULL, CAST(0x0000AC79013167E7 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'893badac-74ac-432c-85c0-7a6da256f2ab', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/304796047', NULL, CAST(0x0000AC79013167E9 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'1ac53098-90a2-4155-9e9d-2f0bc21ae385', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/30781407', NULL, CAST(0x0000AC79013167EA AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'cfdb4f36-3638-4c43-8488-20a2829a7747', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/337047368', NULL, CAST(0x0000AC79013167EC AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'101b10a3-749a-46e5-bdbe-3ad718872f27', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/35184510', NULL, CAST(0x0000AC79013167ED AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'fcd4ee19-f6d7-4330-b374-d0cf66295dac', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/385443952', NULL, CAST(0x0000AC79013167EF AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'c3ae57a3-8959-4a71-a059-14114967c47d', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/400600549', NULL, CAST(0x0000AC79013167F1 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'e34d05f5-921a-4201-9352-784525694c2b', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/409640231', NULL, CAST(0x0000AC79013167F3 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'd731ea74-5615-4b6f-b580-af30e3b4182e', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/412170551', NULL, CAST(0x0000AC79013167F4 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'44dda3e9-8d2d-4658-a16b-d9252bf13199', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/422510526', NULL, CAST(0x0000AC79013167F6 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'bfdafd54-cdb1-43d4-9352-815ee3b5737d', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/424454636', NULL, CAST(0x0000AC79013167F8 AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'a8bfb719-d156-47c0-92e0-f7fa0ffb1e47', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/428054815', NULL, CAST(0x0000AC79013167FA AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'014e1b00-d36b-4159-9133-f3be900002fa', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/428055072', NULL, CAST(0x0000AC79013167FC AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'ee0868bb-ffd9-4099-b29e-c88bcec30301', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/48013132', NULL, CAST(0x0000AC79013167FD AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'77e067b1-c6a5-41ec-8dc7-78570554ae74', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/49311795', NULL, CAST(0x0000AC79013167FF AS DateTime))
GO
INSERT [dbo].[CombinedQuestion] ([ID], [TopCategoryID], [HotWordID], [URL], [Name], [CreateTime]) VALUES (N'ad9c5180-71a3-481d-a0b7-af9be49a6ffd', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/61048115', NULL, CAST(0x0000AC7901316801 AS DateTime))
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDoneBaidu], [isDoneZhihu]) VALUES (N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒评测', 1, 1)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDoneBaidu], [isDoneZhihu]) VALUES (N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒推荐', 1, 1)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDoneBaidu], [isDoneZhihu]) VALUES (N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'2D18549B-36F2-46E2-8E3E-8AF339B07ADC', N'保温壶', 1, 1)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDoneBaidu], [isDoneZhihu]) VALUES (N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'2D18549B-36F2-46E2-8E3E-8AF339B07ADC', N'保温壶玻璃内胆', 1, 1)
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd765dc88-c9b1-40bc-880f-691c170ba136', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/300438772', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'3833060b-2d24-4447-966a-331f1ddc5716', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/37287487', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'880b53e7-4190-42b7-a5d7-ecdff318c505', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/319633948', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e59d957b-c379-41c5-ad98-4d5051324bcd', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/266074134', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'ff28275f-7708-4e77-8698-481074e5d1aa', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/343930158', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'c0bbc3e5-8eab-4713-a285-dc58fcf658b3', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/47501673', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'a04352a9-0693-44d6-8f3c-ba5187ec2115', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/377289258', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'fb28c836-428f-4ca7-9fae-0ea0ad84d100', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/356971382', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'8fb5b172-eccb-4cbf-bd2e-202c0fd6de5c', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/21958857', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd75b3b9e-cc56-4f18-8a30-37126202dc79', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/25501230', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'1139c935-ad99-40f3-888f-f2ab43e01424', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/57443806', N'zhihu', NULL, CAST(0x0000AC79011D0831 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'4dd4ec16-ec45-45fb-aaa5-1f3c53718306', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/36488618', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'5edbbad8-c4fd-492b-a61e-b434736633a9', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/319633948', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'35eb8c59-865f-4f8f-b225-3024623ea1a3', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/333832535', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7f7e4115-8614-4f7f-bb48-7db1f03f5f62', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/350956652', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'9b5d5486-19bb-4c66-a9e7-b4f8507ea451', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/21699754', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'51c72738-7de1-4c8b-bb7e-862536f861c4', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/389486558', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'208bce04-8b8e-4e61-96fd-099c47b86203', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/300438772', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e6169af8-c98f-4183-b9aa-2152fce356e1', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/37287487', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'2d0b86d7-814c-4866-b286-1ea81ed44f2f', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/395437395', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'16467f83-5ae2-48c1-a620-619f10dc4dea', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/27610134', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd11712c6-8ec0-4d6f-bbc7-a360392e3889', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/47501673', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e4a94c28-b172-402d-8f14-25021320e472', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/343930158', N'zhihu', NULL, CAST(0x0000AC79011D2C2D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'2c110103-b506-4542-88b1-641fadbb56c8', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/300438772', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'8bef87db-14e6-4fb8-9d55-da8324abb803', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/389486558', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'28837614-2897-4bd2-9098-410be7cc336d', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/356971382', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'4f217d15-2d15-4fc4-8db4-fec3f69a0c5e', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/27610134', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'73d4d3ec-cbd2-4db7-820e-a9e590aa0e6f', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/265398695', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'4188e527-ffc3-42d0-ae94-02bc5a4c5cd6', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/38860847', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7e23006e-c64a-40ef-b04a-acbfef9697e7', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/366481299', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'ccc4d0ea-c252-4e9a-b878-4599506391aa', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/321887832', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'5a8ca838-81fa-49ac-997c-399ddee9230e', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/47501673', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'6a6b42b6-c9f6-47c6-8e15-806088bf2a00', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/30183628?sort=created', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'8ada54ca-6f99-42e6-be32-ebb93331979c', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/35755175', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd1a86849-0e89-4a71-80cf-0212a0c45a10', N'D171A75B-ED69-4BD8-9028-9CA0184058BA', N'https://www.zhihu.com/question/399372131', N'baidu', NULL, CAST(0x0000AC79011D2F75 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'c1ae7bea-24ed-40cc-9d97-e3c29df451fc', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/36488618', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e6c6ef91-7ce2-4f60-ac1b-e4a72027a315', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/333832535', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e891a2a1-1d7e-466e-8811-12f01731b736', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/319633948', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'fcc84e23-3488-48db-92ce-cdbd3ca5e920', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/395437395', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'84666eb3-0211-4814-a17a-b6b92bb4fd57', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/298716241', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7d7c33c7-9aef-4072-a0f7-61b568aad50c', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/385047025', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'cf321c16-62bb-4e09-8f51-5c4a7c8d8f07', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/300438772', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'4c7b8b0f-31b6-45de-a2f0-d39269005a50', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/61048115', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7d5d9412-bf81-4493-beca-22b8360d542c', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/27531400', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'72fa7261-179b-46b2-9413-659cc6c20cf0', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/424454636', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'9dec167f-e7d0-4ae3-ab47-3b588cd9b8f9', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/293692201', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'6a265ca4-680b-43fc-a45a-ff05751e7582', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/48013132', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'43c3960b-4e9e-40b7-8323-888395ba76ce', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/422510526', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'61b6109f-c6db-4a14-81bf-e4a9705e8099', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/428055072', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'29f56269-254b-4625-b3b8-5167e3fc5e72', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/385443952', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7744ae19-26f9-4d4a-a7ff-b752bb5bdde4', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/337047368', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'358c9d23-54cd-439a-874a-3c6cd45582e4', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/23403081', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'8b2859a2-6fba-45f4-ab3c-fb1c3807e62e', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/412170551', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'9853d8bc-5eff-413a-bc90-fc9a20e81f9b', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/22726851', N'zhihu', NULL, CAST(0x0000AC79010371CB AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e2ea2775-8d70-4a0c-a658-27c5179899d4', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/27531400', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'5ec90c93-3649-4fdd-9590-e8883406ccf4', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/61048115', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7c9a9b70-81f4-4b0d-96a1-a9fa00da85d0', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/422510526', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'815b5b90-81df-4c59-9634-217550349db5', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/37287487', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'1a913438-53aa-4075-981e-30974d9de144', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/276274224', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'9a7869ee-6e55-42af-be48-1e4f31abed3a', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/428054747', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'eafaed1b-d20e-42ba-8af6-b85eed8ea2ed', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/393480397', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'066fe97f-bd15-4f47-a25f-88b20216a3bd', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/28349849', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'49356824-bb82-466a-8767-c9801489c889', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/57662233', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd5dc71f0-f67e-489e-a264-579039d1f199', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/26542497', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'd18496d2-68bf-42cd-9fb6-67bd0a3e1e13', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/412170551', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e3c9d6a6-3d38-4856-a9e4-c2e7d587910e', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/21958857', N'zhihu', NULL, CAST(0x0000AC790103B6F4 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'ffeec71e-15de-4c94-b368-1b5bf7ee1864', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/35184510', N'baidu', NULL, CAST(0x0000AC7901040563 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'391cc5e8-f270-4c81-a828-3bfa823ca7c1', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/20707674', N'baidu', NULL, CAST(0x0000AC7901040563 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'636cf616-c306-449b-b7c3-34c9586134e2', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/23403081', N'baidu', NULL, CAST(0x0000AC7901040563 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'aa3da4a2-e34f-45e8-b84d-b2a24ed1fcb1', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/49311795', N'baidu', NULL, CAST(0x0000AC7901040563 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'fb121a10-02db-43a5-a282-2fac7d7ec42c', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/304796047', N'baidu', NULL, CAST(0x0000AC7901040563 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'82a37ed9-28b1-48ad-a6d7-70be1c568441', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/30781407', N'baidu', NULL, CAST(0x0000AC7901040564 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'8686492e-036b-4074-b2c2-6d41483174ed', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/21416593', N'baidu', NULL, CAST(0x0000AC7901040564 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'7507d089-e73a-4c19-90f0-dc3e55345e5b', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/409640231', N'baidu', NULL, CAST(0x0000AC7901040564 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'e1445cd5-01e2-4a62-bc97-b74a30379079', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/293511214', N'baidu', NULL, CAST(0x0000AC7901043E6D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'9154364b-ef0a-4b25-8ac7-f0114e7f11e1', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/22726851', N'baidu', NULL, CAST(0x0000AC7901043E6D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'57f20cde-c4e5-424b-a49d-20580bdcf61f', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/61048115', N'baidu', NULL, CAST(0x0000AC7901043E6D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'669cb292-849e-494c-89a0-eaa92f49b949', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/284812765', N'baidu', NULL, CAST(0x0000AC7901043E6D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'74824bf6-a3ce-476a-a977-ecfec243d6d7', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/372434384', N'baidu', NULL, CAST(0x0000AC7901043E6D AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'162e8c76-29e7-42a9-9940-c8dd92e9a584', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524', N'baidu', NULL, CAST(0x0000AC7901043E6E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'a677f8ef-8d51-4f29-aab4-e3c5a56436d1', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524?sort=created', N'baidu', NULL, CAST(0x0000AC7901043E6E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'870fafd4-bf38-4eb9-91c7-016ab105da37', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/428054815', N'zhihu', NULL, CAST(0x0000AC7901077745 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'f31bc3f3-f3cb-4bb6-a894-8411f7be16b8', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/400600549', N'zhihu', NULL, CAST(0x0000AC7901077745 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'541a3543-353d-4afa-b612-ccfde5396771', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/21699754', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'f0109da3-163f-4f03-9d52-d55c0628e369', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/399372131', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'6c591eea-a05e-45c5-b853-686589ea81cb', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/37246431', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'1841d3e8-5446-49eb-ac3f-166a58b4eb30', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/28249543', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'a4c20934-5515-41ec-af05-45839f84989e', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/26064447', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'1e460906-0abf-48d6-9e2d-946e35621447', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/375845650', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'a3edc498-aeb2-4577-92d6-ce2b31914591', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/356971382', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'97238c43-3cc6-425f-b043-6154635f97e0', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/47501673', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'27f6a898-3b08-4af1-bf9f-ac0d6c1db2ae', N'E5D8707E-DE98-4499-A562-A96F8EE987D4', N'https://www.zhihu.com/question/265398695', N'baidu', NULL, CAST(0x0000AC79011D491E AS DateTime))
GO
INSERT [dbo].[TopCategory] ([ID], [Name], [IsActive]) VALUES (N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒', 1)
GO
INSERT [dbo].[TopCategory] ([ID], [Name], [IsActive]) VALUES (N'2D18549B-36F2-46E2-8E3E-8AF339B07ADC', N'保温壶', 0)
GO
INSERT [dbo].[XZSE86] ([ID], [TopCategoryID], [XZSE86JSON]) VALUES (N'AC7372E5-A36D-4E43-835A-9B61C9EE3E5E', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'"[{\"hotword\": \"保温饭盒评测\", \"originStr\": \"\", \"md5\": \"\", \"xZse86Val\": \"1.0_aMYBgU9BnwNpNw20TGF0gbXBk7YxgqO818t0rAX0kLFp\"}, {\"hotword\": \"保温饭盒推荐\", \"originStr\": \"\", \"md5\": \"\", \"xZse86Val\": \"1.0_aTxySAH0rTFxeLSq1HYyS6U8b7FXkTxBsXt0SHUqNBNX\"}]"')
GO
INSERT [dbo].[XZSE86] ([ID], [TopCategoryID], [XZSE86JSON]) VALUES (N'C330BA18-A86C-49BE-97EB-A36369A07570', N'2D18549B-36F2-46E2-8E3E-8AF339B07ADC', N'"[{\"hotword\": \"保温壶\", \"originStr\": \"\", \"md5\": \"\", \"xZse86Val\": \"1.0_a0t0NJ9BHCSpFCOq09FBHvX0bR2pNwF0fTY0QTu0nwFx\"}, {\"hotword\": \"保温壶玻璃内胆\", \"originStr\": \"\", \"md5\": \"\", \"xZse86Val\": \"1.0_a_Y0cAr8FuFXrLx0M_xBFcUBS8Ypr8OB17Y8Q790QTtx\"}]"')
GO
