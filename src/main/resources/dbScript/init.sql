USE [HaoWu]
GO
/****** Object:  Table [dbo].[HotWord]    Script Date: 2020-11-19 18:23:09 ******/
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
	[isDone] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Question]    Script Date: 2020-11-19 18:23:09 ******/
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
/****** Object:  Table [dbo].[QuestionContent]    Script Date: 2020-11-19 18:23:09 ******/
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
/****** Object:  Table [dbo].[TopCategory]    Script Date: 2020-11-19 18:23:09 ******/
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
/****** Object:  Table [dbo].[XZSE86]    Script Date: 2020-11-19 18:23:09 ******/
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
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒评测', 1)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒推荐', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'0b99315b-7f76-43a6-b832-706a30ce0fb6', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶玻璃内胆', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'01b6c371-be7f-475e-93e0-d53add3fcc90', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶不保温', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'09a4c9a0-155a-4990-bc14-c2e6b8090b84', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶测评', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'29426bb6-941b-4153-9a13-62538f09bbc9', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶户外', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'8e4cfebb-08aa-4345-b381-2961141d3e0b', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶 推荐', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'360c8122-1cae-4c83-85a4-9751da11ede9', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶 品牌', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'b05f4ad4-9b57-444c-adc8-131a3594280a', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶 玻璃内胆', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'72cf4cc2-c0ec-4f0f-b884-0b32e67ce2d6', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶 内胆', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'aff083c9-cd8f-47c0-a526-a1d942cb8a21', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶家用', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'30cd35a4-369f-4222-a6fe-e5ff48d2e757', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶哪个牌子好', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'6e3c0326-43ce-4055-9890-9cc750b6b0f2', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶内胆', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'77076747-dd1d-4818-8402-43114d3dae65', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶哪个好', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'cf8f4b4b-f8da-40cb-a757-9878b62042c8', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶品牌', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'906aa23c-b7a7-401a-a112-2a93357b0446', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶牌子', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'23f818de-f1c5-44c8-aa8a-905992314ef6', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶泡茶', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'f845000d-964e-4a59-a59f-38561ae01fe8', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶清洗', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'6ba9be09-1eaa-4ec5-a2a9-dc8a2c9e3e80', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶什么牌子好', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'85deaf27-83c0-4ef0-b681-1f45a9ea8849', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶什么内胆好', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'eb78e81a-bb0f-404d-8df3-211a205a19f9', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶宿舍', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'6be26791-5a83-4888-bfa3-99d700fa5c5b', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶水垢', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'556828aa-f4c3-44e4-8090-25910245ac0c', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶推荐', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'0fb745ee-13ba-4b14-a602-9f2eb6f16919', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶 玻璃 不锈钢', NULL)
GO
INSERT [dbo].[HotWord] ([ID], [TopCategoryID], [Name], [isDone]) VALUES (N'b12af293-2618-4045-b426-88de8d4267df', N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶怎么选', NULL)
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'400d213a-0f64-45d4-9120-61491eabec05', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/35184510', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'2a979a2b-3eb0-4ff8-a23a-7a99e75a5d94', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/20707674', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'20d7cedf-c951-4c28-ab12-f38cf504ff42', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/23403081', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'be890fce-78b9-400c-85af-7bea73348892', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/49311795', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'af3edbea-ada2-4133-8ac2-949fcf6b6239', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/304796047', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'f6d41742-7909-4a67-9a8d-2e02ddfd5d7b', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/30781407', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'39cb1724-4c00-487e-a50a-405e0b86284c', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/394331181', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'0841c89e-e340-4d43-9350-0dd1c1989358', N'4F03AAE4-FA74-47E8-A59D-305DE39A6276', N'https://www.zhihu.com/question/409640231', N'baidu', NULL, CAST(0x0000AC78012D32B0 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'1b39d3fa-1bc5-4d0c-a9f8-82dd2829c706', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/293511214', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'2167c846-4a33-471b-8600-f85cd1270415', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/22726851', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'bedd1720-0f82-4043-8840-d1cebcb293c7', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/61048115', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'ba964772-4f4d-4622-b18a-a5cb37cd141a', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/284812765', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'64c16d6d-8425-41dc-a73b-1f1fccb21b96', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/372434384', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'fc5d51db-8a0a-427d-bbfb-8f268ea25b4c', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[Question] ([ID], [HotWordID], [URL], [Source], [Name], [CreateTime]) VALUES (N'4363938d-78b0-4771-b6cb-af15abf5614a', N'1A749203-A9DF-4BD7-AFC5-6FFAC6D20E89', N'https://www.zhihu.com/question/271644524?sort=created', N'baidu', NULL, CAST(0x0000AC78012DAE20 AS DateTime))
GO
INSERT [dbo].[TopCategory] ([ID], [Name], [IsActive]) VALUES (N'6AC1705C-1BCB-49F6-AB19-46667E13A1CB', N'保温饭盒', 1)
GO
INSERT [dbo].[TopCategory] ([ID], [Name], [IsActive]) VALUES (N'd9bd9f5a-9b73-4e28-90d6-a85f2e708210', N'保温壶', 1)
GO
