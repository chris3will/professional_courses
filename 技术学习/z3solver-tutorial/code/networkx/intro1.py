'''
Author: Chris Wang
Date: 2021-02-23 08:28:07
LastEditors: your name
LastEditTime: 2021-02-23 08:40:46
Description: file content
'''

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import networkx as nx

G = nx.barabasi_albert_graph(100,2)

# network analysis is to understand the relationships between entities, we can use number of methodological and theoretical tools

nx.draw_spring(G)