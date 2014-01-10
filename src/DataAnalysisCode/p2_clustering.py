'''
Created on Oct 20, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx
mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', create_using=nx.Graph())
print("Global Clustering is")
print(nx.transitivity(mygraph)) #Transitivity is networkx function to calculate Global Clustering
print("local Clustering is")
local_clustering=nx.clustering(mygraph) #Transitivity is networkx function to calculate Local Clustering
avg=0
count=0

for i in local_clustering:
    count=count+1
    avg=avg+local_clustering[i]
    
print("Local Clustering is")
print(avg/count)