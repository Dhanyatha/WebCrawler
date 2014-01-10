'''
Created on Oct 21, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx

mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', data="false")
node_count=mygraph.number_of_nodes()
node_list=mygraph.nodes()


#################################################################
count=0
current_pair=0
for index in range(0,node_count):
    node=node_list[index]
    for next_node in range((index+1),node_count):
        count=count+1
        temp=node_list[next_node]
        first_node=set(nx.neighbors(mygraph,node))
        second_node=set(nx.neighbors(mygraph,temp))
        union=first_node | second_node
        intersection=first_node & second_node
        jaccard_similarity=len(intersection)/len(union)
        if(current_pair<jaccard_similarity):
            current_pair=jaccard_similarity
            
print("Very Similar node pair is: "+str(node)+" and "+str(temp))
print("Similarity: " +str(current_pair))
